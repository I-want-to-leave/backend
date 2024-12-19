package com.travel.leave.domain.ai_travel.controller;

import com.travel.leave.domain.ai_travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.domain.ai_travel.dto.ai_recommend.TripRequestDTO;
import com.travel.leave.domain.ai_travel.service.recommend.TripCacheService;
import com.travel.leave.domain.ai_travel.service.recommend.AiTripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
public class TripController {

    private final AiTripService AITripService;
    private final TripCacheService tripCacheService;

    @PostMapping("/recommend")
    public ResponseEntity<RecommendDTO> createTrip(@RequestBody TripRequestDTO tripRequest, @AuthenticationPrincipal Long userCode) {
        RecommendDTO recommendDTO = AITripService.createTripPlanWithCache(userCode, tripRequest);
        return ResponseEntity.status(HttpStatus.OK).body(recommendDTO);
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptTripRecommendation(@AuthenticationPrincipal Long userCode) {
        AITripService.saveTripPlan(userCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/reject")
    public ResponseEntity<Void> deleteSavedTrip(@AuthenticationPrincipal Long userCode) {
        tripCacheService.deleteTripPlan(userCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}