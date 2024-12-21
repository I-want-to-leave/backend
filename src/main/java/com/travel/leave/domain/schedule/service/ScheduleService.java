package com.travel.leave.domain.schedule.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.travel.leave.domain.ai_travel.dto.gpt.GPTRequest;
import com.travel.leave.domain.ai_travel.dto.gpt.GPTResponse;
import com.travel.leave.domain.ai_travel.mapper.AI_Mapper.PromptMapper;
import com.travel.leave.domain.schedule.dto.get.CheckResponseDTO;
import com.travel.leave.subdomain.travel.entity.Travel;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import com.travel.leave.subdomain.usertravel.entity.UserTravel;
import com.travel.leave.domain.schedule.dto.get.TravelRequestDTO;
import com.travel.leave.domain.schedule.dto.get.invite.UserInviteDTOs;
import com.travel.leave.domain.schedule.service.cache.TravelCache;
import com.travel.leave.domain.schedule.service.cache.handler.TravelCacheHandler;
import com.travel.leave.subdomain.travel.service.TravelService;
import com.travel.leave.subdomain.travellocaion.service.TravelLocationService;
import com.travel.leave.subdomain.usertravel.service.UserTravelService;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {
    @Value("${gpt.model}")
    private String model;

    private final WebClient gptWebClient;
    private final TravelCacheHandler travelCacheHandler;
    private final TravelService travelService;
    private final TravelLocationService travelLocationService;
    private final UserTravelService userTravelService;

    public UserInviteDTOs getUsersForInvite(String emailKeyword) {
        return userTravelService.getUsersForInvite(emailKeyword);
    }

    public TravelCache loadTravel(Long travelCode) {
        if (travelCacheHandler.hasTravel(travelCode)) {
            return travelCacheHandler.loadTravel(travelCode, true);
        }
        return travelCacheHandler.loadTravel(travelCode, false);
    }

    @Transactional @SuppressWarnings("unused") //나중에 리팩터링할 때 사진 저장로직 실행시간 점검 후 트랜젝션 분리 요망
    public TravelCache initializeTravel(Long userCode, TravelRequestDTO travelRequestDTO) {
        Travel savedTravel = travelService.save(travelRequestDTO);  //여행 저장
        List<TravelLocation> savedTravelLocations = travelLocationService.save(savedTravel.getCode(), travelRequestDTO.schedule()); //여행 스케쥴 저장
        List<UserTravel> savedUserTravels = userTravelService.save(savedTravel.getCode(), userCode, travelRequestDTO.member()); //여행에 포함된 멤버 저장
        log.info("ddd");
        return travelCacheHandler.loadTravel(savedTravel.getCode(), false);
    }

    public CheckResponseDTO checkSchedule(Long travelCode) throws JsonProcessingException {
        TravelCache travelCache = loadTravel(travelCode);
        String prompt = PromptMapper.generatePrompt2(travelCache);
        GPTRequest gptRequest = new GPTRequest(model, prompt);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 디버깅: GPT에 넘기기 전 상태 로깅
        try {
            log.info("전송할 TravelCache(JSON): {}", objectMapper.writeValueAsString(travelCache));
        } catch (Exception ex) {
            log.error("직렬화 오류", ex);
        }

        try {
            return gptWebClient.post()
                    .bodyValue(gptRequest)
                    .retrieve()
                    .bodyToMono(GPTResponse.class)
                    .doOnNext(response -> log.info("GPT 응답: {}", response))
                    .map(gptResponse -> {
                        CheckResponseDTO checkResponseDTO = new CheckResponseDTO();
                        checkResponseDTO.setGptCheckResponse(gptResponse.getChoices().get(0).getMessage().getContent());
                        return checkResponseDTO;
                    })
                    .toFuture()
                    .get(1000, TimeUnit.SECONDS);

        } catch (TimeoutException e) {
            log.error("GPT API call timed out", e);
            throw new RuntimeException("Request timeout: GPT API did not respond in time.");
        } catch (Exception e) {
            log.error("Error during GPT API call", e);
            throw new RuntimeException("Internal server error: GPT API call failed.", e);
        }
    }
}