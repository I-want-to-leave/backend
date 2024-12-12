package com.travel.leave.travel.mapper.AI_Mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.travel.leave.entity.UserTravel;
import com.travel.leave.travel.dto.ai_recommend.LatLngDTO;
import com.travel.leave.travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.travel.dto.ai_recommend.RecommendedItemDTO;
import com.travel.leave.travel.dto.ai_recommend.TripRequestDTO;
import com.travel.leave.travel.entity.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class AI_TripMapper {

    public static RecommendDTO toRecommendDTO(
            JsonNode rootNode,
            List<List<LatLngDTO>> dailyCoordinates,
            List<RecommendedItemDTO> recommendedItems,
            TripRequestDTO tripRequest,
            String imageUrl
    ) {

        String tripName = rootNode.get("tripName").asText();
        String tripExplanation = rootNode.get("tripExplanation").asText();
        int totalCost = rootNode.get("totalCost").asInt();

        RecommendDTO recommendDTO = new RecommendDTO();
        recommendDTO.setTripName(tripName);
        recommendDTO.setTripExplanation(tripExplanation);
        recommendDTO.setImageUrl(imageUrl);
        recommendDTO.setTotalCost(totalCost);
        recommendDTO.setDailyRoutes(dailyCoordinates);
        recommendDTO.setRecommendedItems(recommendedItems);
        recommendDTO.setStartDate(tripRequest.getStartDate());
        recommendDTO.setEndDate(tripRequest.getEndDate());

        return recommendDTO;
    }

    public static Travel toTravelEntity(RecommendDTO recommendDTO) {
        return new Travel(
                null,
                recommendDTO.getTripName(),
                recommendDTO.getTripExplanation(),
                new Timestamp(System.currentTimeMillis()),
                null,
                Date.valueOf(recommendDTO.getStartDate()),
                Date.valueOf(recommendDTO.getEndDate()),
                recommendDTO.getImageUrl()
        );
    }

    public static TravelLocation toTravelLocationEntity(LatLngDTO latLngDTO, Long travelCode) {
        return new TravelLocation(
                null,
                new ScheduleDetails(
                        latLngDTO.getPlaceName(),
                        "",
                        latLngDTO.getStartAt(),
                        latLngDTO.getEndAt(),
                        latLngDTO.getStepOrder(),
                        new GeographicDetails(
                                BigDecimal.valueOf(latLngDTO.getLat()),
                                BigDecimal.valueOf(latLngDTO.getLng())
                        )
                ),
                travelCode
        );
    }

    public static TravelPreparation toTravelPreparationEntity(RecommendedItemDTO itemDTO, Long travelCode) {
        return new TravelPreparation(
                null,
                itemDTO.getItemName(),
                "",
                itemDTO.getQuantity(),
                travelCode
        );
    }

    public static UserTravel toUserTravelEntity(Long travelCode, Long userCode) {
        return new UserTravel(
                null,
                travelCode,
                userCode
        );
    }
}