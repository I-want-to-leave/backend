package com.travel.leave.travel.mapper.AI_Mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.leave.exception.BadReqeust.GPTResponseParsingException;
import com.travel.leave.travel.service.TransportType;
import com.travel.leave.travel.dto.ai_recommend.LatLngDTO;
import com.travel.leave.travel.dto.ai_recommend.RecommendedItemDTO;
import com.travel.leave.travel.dto.gpt.GPTResponse;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
                ZonedDateTime startZoned = ZonedDateTime.of(LocalDateTime.parse(stepNode.get("startAt").asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ZoneId.of("UTC"));
                ZonedDateTime endZoned = ZonedDateTime.of(LocalDateTime.parse(stepNode.get("endAt").asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ZoneId.of("UTC"));
                Timestamp startAt = Timestamp.from(startZoned.toInstant());
                Timestamp endAt = Timestamp.from(endZoned.toInstant());

                int stepOrder = stepNode.get("stepOrder").asInt();
                daySteps.add(new LatLngDTO(placeName, 0.0, 0.0, stepOrder, startAt, endAt, transportType));
            }
            gptDailyRoutes.add(daySteps);
        }
        return gptDailyRoutes;
    }
}