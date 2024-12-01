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

    @Async("Executor")
    public CompletableFuture<RecommendDTO> createTripPlan(TripRequestDTO tripRequestDTO) {

        CompletableFuture<GPTResponse> gptResponseFuture = gptService.getRecommendation(tripRequestDTO)
                .thenApply(ResponseEntity::getBody);

        CompletableFuture<List<List<LatLngDTO>>> dailyCoordinatesFuture = gptResponseFuture.thenCompose(gptResponse ->
                googleMapsService.processDailyRoutes(gptService.extractDailyRoutes(gptResponse)));

        return CompletableFuture.allOf(gptResponseFuture, dailyCoordinatesFuture)
                .thenApply(v -> {
                    GPTResponse gptResponse = gptResponseFuture.join();
                    List<List<LatLngDTO>> dailyCoordinates = dailyCoordinatesFuture.join();
                    List<RecommendedItemDTO> recommendedItems = gptService.extractRecommendedItems(gptResponse);
                    JsonNode rootNode = GPTResponseMapper.parseResponseContent(gptResponse);
                    return TripMapper.toRecommendDTO(rootNode, dailyCoordinates, recommendedItems, tripRequestDTO);
                });
    }

    public CompletableFuture<RecommendDTO> createTripPlanWithCache(Long userCode, TripRequestDTO tripRequest) {
        return createTripPlan(tripRequest)
                .thenApply(tripPlan -> {
                    try {
                        tripCacheService.saveTripPlan(userCode, tripPlan);
                        return tripPlan;
                    } catch (Exception ex) {
                        throw new TripCacheSaveException("캐시에 저장 실패");
                    }
                });
    }
}