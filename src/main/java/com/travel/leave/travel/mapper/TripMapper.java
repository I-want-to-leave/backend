package com.travel.leave.travel.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.travel.leave.travel.dto.ai.LatLngDTO;
import com.travel.leave.travel.dto.ai.RecommendDTO;
import com.travel.leave.travel.dto.ai.RecommendedItemDTO;
import com.travel.leave.travel.dto.ai.TripRequestDTO;

import java.util.List;

public class TripMapper {

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
}

