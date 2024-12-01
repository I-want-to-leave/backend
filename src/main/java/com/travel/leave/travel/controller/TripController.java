package com.travel.leave.travel.controller;

import com.travel.leave.travel.dto.ai.RecommendDTO;
import com.travel.leave.travel.dto.ai.TripRequestDTO;
import com.travel.leave.travel.service.TripCacheService;
import com.travel.leave.travel.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
    private final TripCacheService tripCacheService;

    @PostMapping("/recommend")
    public CompletableFuture<ResponseEntity<RecommendDTO>> createTrip(@RequestBody TripRequestDTO tripRequest, @AuthenticationPrincipal Long userCode) {
        return tripService.createTripPlanWithCache(userCode, tripRequest)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
    }

    @DeleteMapping("/reject")
    public ResponseEntity<Void> deleteSavedTrip(@AuthenticationPrincipal Long userCode) {
        tripCacheService.deleteTripPlan(userCode);
        return ResponseEntity.noContent().build();
    }
}
