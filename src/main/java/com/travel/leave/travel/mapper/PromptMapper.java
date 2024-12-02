package com.travel.leave.travel.mapper;

import com.travel.leave.travel.dto.ai.TripRequestDTO;
import com.travel.leave.travel.util.PromptLoader;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PromptMapper {

    private static final String promptTemplate = PromptLoader.loadPrompt("classpath:prompts/trip_prompt.txt");

    public static String generatePrompt(TripRequestDTO tripRequest) {
        return String.format(promptTemplate,
                tripRequest.getStartLocation(),
                tripRequest.getStartDate(),
                tripRequest.getEndDate(),
                tripRequest.getKeywords(),
                tripRequest.isCarOwned());
    }
}
