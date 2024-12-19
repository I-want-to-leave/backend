package com.travel.leave.domain.ai_travel.service.recommend;

import com.fasterxml.jackson.databind.JsonNode;
import com.travel.leave.domain.ai_travel.dto.ai_recommend.LatLngDTO;
import com.travel.leave.domain.ai_travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.domain.ai_travel.dto.ai_recommend.RecommendedItemDTO;
import com.travel.leave.domain.ai_travel.dto.ai_recommend.TripRequestDTO;
import com.travel.leave.domain.ai_travel.dto.gpt.GPTResponse;
import com.travel.leave.domain.ai_travel.exception.RedisCacheSaveFailException;
import com.travel.leave.domain.ai_travel.mapper.AI_Mapper.AI_TripMapper;
import com.travel.leave.domain.ai_travel.mapper.AI_Mapper.GPTResponseMapper;
import com.travel.leave.domain.ai_travel.service.ai.GPTService;
import com.travel.leave.domain.ai_travel.service.ai.GoogleMapsService;
import com.travel.leave.domain.ai_travel.service.ai.GoogleTranslateService;
import com.travel.leave.domain.ai_travel.service.ai.UnsplashService;
import com.travel.leave.domain.ai_travel.exception.RedisCacheFailException;
import com.travel.leave.domain.ai_travel.exception.AiExceptionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiTripService {

    private final GPTService gptService;
    private final GoogleMapsService googleMapsService;
    private final TripCacheService tripCacheService;
    private final UnsplashService unsplashService;
    private final GoogleTranslateService googleTranslateService;
    private final AiTripPersistenceService AITripPersistenceService;

    @Async("AI_Executor")
    public CompletableFuture<RecommendDTO> createTripPlan(TripRequestDTO tripRequestDTO) {

        // GPT 응답 본문 받아온다
        CompletableFuture<GPTResponse> gptResponseFuture = gptService.getRecommendation(tripRequestDTO)
                .thenApply(ResponseEntity::getBody);

        // GPT 응답을 파싱해서 구글 맵 API 전달 후, 좌표 반환 뒤 하루 별 코스마다, 이중 리스트 처리 후 매핑
        CompletableFuture<List<List<LatLngDTO>>> dailyCoordinatesFuture = gptResponseFuture.thenCompose(gptResponse ->
                googleMapsService.processDailyRoutes(gptService.extractDailyRoutes(gptResponse)));

        // 요청 DTO 의 키워드 맞추어서 이미지 추천
        CompletableFuture<ResponseEntity<String>> imageFuture = unsplashService.fetchImageUrl(tripRequestDTO.getKeywords());

        // allOf 메서드로 모든 위의 실행들을 "모두 끝난 뒤에" 하나로 합쳐서 응답함. (병렬처리 비동기)
        return CompletableFuture.allOf(gptResponseFuture, dailyCoordinatesFuture, imageFuture)
                .thenCompose(v -> {
                    GPTResponse gptResponse = gptResponseFuture.join();
                    String imageUrl = imageFuture.join().getBody();
                    List<List<LatLngDTO>> dailyCoordinates = dailyCoordinatesFuture.join();
                    List<RecommendedItemDTO> recommendedItems = gptService.extractRecommendedItems(gptResponse);
                    JsonNode rootNode = GPTResponseMapper.parseResponseContent(gptResponse);
                    RecommendDTO recommendDTO = AI_TripMapper.toRecommendDTO(
                            rootNode, dailyCoordinates, recommendedItems, tripRequestDTO, imageUrl
                    );

                    return googleTranslateService.translateRecommendDTO(recommendDTO);
                });
    }

    @Transactional
    public RecommendDTO createTripPlanWithCache(Long userCode, TripRequestDTO tripRequest) {
        try {
            RecommendDTO recommendDTO = createTripPlan(tripRequest).join();
            tripCacheService.saveTripPlan(userCode, recommendDTO);
            return recommendDTO;
        } catch (Exception e) {
            log.error("해당 유저코드에 관해 여행 캐싱이 실패하였습니다: {}, tripRequest: {}, error: {}", userCode, tripRequest, e.getMessage(), e);
            throw new RedisCacheFailException(AiExceptionMessage.REDIS_CACHE_FAIL);
        }
    }

    @Transactional
    public void saveTripPlan(Long userCode) {
        RecommendDTO recommendDTO = tripCacheService.getTripPlan(userCode);
        if (recommendDTO == null) {
            throw new RedisCacheSaveFailException(AiExceptionMessage.REDIS_CACHE_SAVE_FAIL);
        }
        AITripPersistenceService.saveAllTripData(recommendDTO, userCode);
    }
}