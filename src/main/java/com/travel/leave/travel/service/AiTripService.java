package com.travel.leave.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.travel.leave.exception.BadReqeust.TripCacheSaveException;
import com.travel.leave.travel.dto.ai_recommend.LatLngDTO;
import com.travel.leave.travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.travel.dto.ai_recommend.RecommendedItemDTO;
import com.travel.leave.travel.dto.ai_recommend.TripRequestDTO;
import com.travel.leave.travel.dto.gpt.GPTResponse;
import com.travel.leave.travel.mapper.AI_Mapper.GPTResponseMapper;
import com.travel.leave.travel.mapper.AI_Mapper.AI_TripMapper;
import com.travel.leave.travel.service.gpt.GPTService;
import com.travel.leave.travel.service.gpt.GoogleMapsService;
import com.travel.leave.travel.service.gpt.UnsplashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AiTripService {

    private final GPTService gptService;
    private final GoogleMapsService googleMapsService;
    private final TripCacheService tripCacheService;
    private final UnsplashService unsplashService;
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
        CompletableFuture<String> imageFuture = unsplashService.fetchImageUrl(tripRequestDTO.getKeywords());

        // allOf 메서드로 모든 위의 실행들을 "모두 끝난 뒤에" 하나로 합쳐서 응답함. (병렬처리 비동기)
        return CompletableFuture.allOf(gptResponseFuture, dailyCoordinatesFuture, imageFuture)
                .thenApply(v -> {
                    GPTResponse gptResponse = gptResponseFuture.join();
                    String imageUrl = imageFuture.join();
                    List<List<LatLngDTO>> dailyCoordinates = dailyCoordinatesFuture.join();
                    List<RecommendedItemDTO> recommendedItems = gptService.extractRecommendedItems(gptResponse);
                    JsonNode rootNode = GPTResponseMapper.parseResponseContent(gptResponse);
                    return AI_TripMapper.toRecommendDTO(rootNode, dailyCoordinates, recommendedItems, tripRequestDTO, imageUrl);
                });
    }

    @Transactional
    public RecommendDTO createTripPlanWithCache(Long userCode, TripRequestDTO tripRequest) {
        try {
            RecommendDTO tripPlan = createTripPlan(tripRequest).join();
            tripCacheService.saveTripPlan(userCode, tripPlan);
            return tripPlan;
        } catch (Exception e) {
            throw new TripCacheSaveException("캐시 저장 실패");
        }
    }

    @Transactional
    public Long saveTripPlan(Long userCode) {
        RecommendDTO recommendDTO = tripCacheService.getTripPlan(userCode);
        if (recommendDTO == null) {
            throw new IllegalStateException("캐시 데이터가 소실 되었습니다. 다시 시도 하십시오");
        }
        return AITripPersistenceService.saveAllTripData(recommendDTO, userCode);
    }
}