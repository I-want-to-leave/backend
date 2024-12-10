package com.travel.leave.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.travel.leave.exception.BadReqeust.TripCacheSaveException;
import com.travel.leave.travel.dto.ai.LatLngDTO;
import com.travel.leave.travel.dto.ai.RecommendDTO;
import com.travel.leave.travel.dto.ai.RecommendedItemDTO;
import com.travel.leave.travel.dto.ai.TripRequestDTO;
import com.travel.leave.travel.dto.gpt.GPTResponse;
import com.travel.leave.travel.mapper.GPTResponseMapper;
import com.travel.leave.travel.mapper.TripMapper;
import com.travel.leave.travel.service.gpt.GPTService;
import com.travel.leave.travel.service.gpt.GoogleMapsService;
import com.travel.leave.travel.service.gpt.UnsplashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TripService {

    private final GPTService gptService;
    private final GoogleMapsService googleMapsService;
    private final TripCacheService tripCacheService;
    private final UnsplashService unsplashService;

    @Async("AI_Executor")
    public CompletableFuture<RecommendDTO> createTripPlan(TripRequestDTO tripRequestDTO) {
        //gpt 응답 비동기로 받아옴
        CompletableFuture<GPTResponse> gptResponseFuture = gptService.getRecommendation(tripRequestDTO)
                .thenApply(ResponseEntity::getBody);
        //gpt 응답이
        CompletableFuture<List<List<LatLngDTO>>> dailyCoordinatesFuture = gptResponseFuture.thenCompose(gptResponse ->
                googleMapsService.processDailyRoutes(gptService.extractDailyRoutes(gptResponse)));

        CompletableFuture<String> imageFuture = unsplashService.fetchImageUrl(tripRequestDTO.getKeywords());

        return CompletableFuture.allOf(gptResponseFuture, dailyCoordinatesFuture, imageFuture)
                .thenApply(v -> {
                    GPTResponse gptResponse = gptResponseFuture.join();
                    String imageUrl = imageFuture.join();
                    List<List<LatLngDTO>> dailyCoordinates = dailyCoordinatesFuture.join();
                    List<RecommendedItemDTO> recommendedItems = gptService.extractRecommendedItems(gptResponse);
                    JsonNode rootNode = GPTResponseMapper.parseResponseContent(gptResponse);
                    return TripMapper.toRecommendDTO(rootNode, dailyCoordinates, recommendedItems, tripRequestDTO, imageUrl);
                });
    }

    public CompletableFuture<RecommendDTO> createTripPlanWithCache(Long userCode, TripRequestDTO tripRequest) {
        return createTripPlan(tripRequest)
                .thenApply(tripPlan -> {
                    try {
                        tripCacheService.saveTripPlan(userCode, tripPlan);
                        return tripPlan;
                    } catch (Exception ex) {
                        System.out.println("캐시에 저장 실패");
                        throw new TripCacheSaveException("캐시에 저장 실패");
                    }
                });
    }
}