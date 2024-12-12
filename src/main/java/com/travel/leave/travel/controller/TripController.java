package com.travel.leave.travel.controller;

import com.travel.leave.travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.travel.dto.ai_recommend.TripRequestDTO;
import com.travel.leave.travel.service.TripCacheService;
import com.travel.leave.travel.service.AiTripService;
import lombok.RequiredArgsConstructor;
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
        RecommendDTO tripPlan = AITripService.createTripPlanWithCache(userCode, tripRequest);
        return ResponseEntity.ok(tripPlan);
    }

    @PostMapping("/accept")
    public ResponseEntity<Long> acceptTripRecommendation(@AuthenticationPrincipal Long userCode) {
        Long travelId = AITripService.saveTripPlan(userCode);
        return ResponseEntity.ok(travelId);
    }

    @DeleteMapping("/reject")
    public ResponseEntity<Void> deleteSavedTrip(@AuthenticationPrincipal Long userCode) {
        tripCacheService.deleteTripPlan(userCode);
        return ResponseEntity.noContent().build();
    }
}