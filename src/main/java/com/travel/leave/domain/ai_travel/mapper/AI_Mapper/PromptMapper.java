package com.travel.leave.domain.ai_travel.mapper.AI_Mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.travel.leave.domain.ai_travel.dto.ai_recommend.TripRequestDTO;
import com.travel.leave.domain.ai_travel.util.PromptLoader;
import com.travel.leave.domain.schedule.service.cache.TravelCache;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PromptMapper {

    private static final String promptTemplate = PromptLoader.loadPrompt("classpath:prompts/trip_prompt.txt");
    private static final String promptTemplate2 = PromptLoader.loadPrompt("classpath:prompts/check_prompt.txt");

    public static String generatePrompt(TripRequestDTO tripRequest) {
        return String.format(promptTemplate,
                tripRequest.getStartLocation(),
                tripRequest.getStartDate(),
                tripRequest.getEndDate(),
                tripRequest.getKeywords(),
                tripRequest.isCarOwned());
    }

    public static String generatePrompt2(TravelCache travelCache) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return String.format(promptTemplate2,
                objectMapper.writeValueAsString(travelCache));
    }
}