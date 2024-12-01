package com.travel.leave.travel.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.leave.exception.BadReqeust.GPTResponseParsingException;
import com.travel.leave.travel.TransportType;
import com.travel.leave.travel.dto.ai.LatLngDTO;
import com.travel.leave.travel.dto.ai.RecommendedItemDTO;
import com.travel.leave.travel.dto.gpt.GPTResponse;

import java.util.ArrayList;
import java.util.List;

public class GPTResponseMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode parseResponseContent(GPTResponse gptResponse) {
        try {
            String responseContent = gptResponse.getChoices().get(0).getMessage().getContent();
            return objectMapper.readTree(responseContent);
        } catch (JsonProcessingException e) {
            throw new GPTResponseParsingException("AI 생성에 오류가 났습니다", e);
        }
    }

    public static List<RecommendedItemDTO> toRecommendedItems(JsonNode rootNode) {
        List<RecommendedItemDTO> recommendedItems = new ArrayList<>();
        if (rootNode.has("recommendedItems")) {
            for (JsonNode itemNode : rootNode.get("recommendedItems")) {
                RecommendedItemDTO recommendedItemDTO = new RecommendedItemDTO();
                recommendedItemDTO.setItemName(itemNode.get("itemName").asText());
                recommendedItemDTO.setQuantity(itemNode.get("quantity").asInt());
                recommendedItems.add(recommendedItemDTO);
            }
        }
        return recommendedItems;
    }

    public static List<List<LatLngDTO>> toDailyRoutes(JsonNode rootNode) {
        List<List<LatLngDTO>> gptDailyRoutes = new ArrayList<>();
        for (JsonNode dayNode : rootNode.get("dailyRoutes")) {
            List<LatLngDTO> daySteps = new ArrayList<>();
            for (JsonNode stepNode : dayNode) {
                String placeName = stepNode.get("stepName").asText();
                TransportType transportType = TransportType.valueOf(stepNode.get("transportType").asText().toUpperCase());
                daySteps.add(new LatLngDTO(placeName, 0.0, 0.0, transportType));
            }
            gptDailyRoutes.add(daySteps);
        }
        return gptDailyRoutes;
    }
}