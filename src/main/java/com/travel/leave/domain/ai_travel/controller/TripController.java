package com.travel.leave.domain.ai_travel.controller;

import com.travel.leave.domain.ai_travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.domain.ai_travel.dto.ai_recommend.TripRequestDTO;
import com.travel.leave.domain.ai_travel.service.recommend.AiTripService;
import com.travel.leave.domain.ai_travel.service.recommend.TripCacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AI 여행 코스 추천 컨트롤러", description = "AI 를 이용한 여행 코스 추천")
@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
public class TripController {

    private final AiTripService AITripService;
    private final TripCacheService tripCacheService;

    @Operation(summary = "여행을 생성하고 캐시에 저장합니다", description = "수락과 거절을 통해 정보를 취사선택 할 수 있습니다")
    @ApiResponse(responseCode = "200", description = "추천 여행 계획 생성 성공", content = @Content( schema = @Schema(implementation = RecommendDTO.class)))
    @PostMapping("/recommend")
    public ResponseEntity<RecommendDTO> createTrip(
            @RequestBody TripRequestDTO tripRequest, @AuthenticationPrincipal Long userCode) {
        RecommendDTO recommendDTO = AITripService.createTripPlanWithCache(userCode, tripRequest);
        return ResponseEntity.status(HttpStatus.OK).body(recommendDTO);
    }

    @Operation(summary = "추천 여행 계획 저장", description = "Redis 에 저장된 추천 여행 계획 데이터를 사용자 데이터베이스에 저장합니다.")
    @ApiResponse(responseCode = "200", description = "추천 여행 계획을 내 여행으로 저장 성공")
    @PostMapping("/accept")
    public ResponseEntity<Void> acceptTripRecommendation(@AuthenticationPrincipal Long userCode) {
        AITripService.saveTripPlan(userCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "추천 여행 계획 삭제", description = "Redis 에 저장된 추천 여행 계획 데이터를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "추천 여행 계획 캐시 삭제 성공")
    @DeleteMapping("/reject")
    public ResponseEntity<Void> deleteSavedTrip(@AuthenticationPrincipal Long userCode) {
        tripCacheService.deleteTripPlan(userCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}