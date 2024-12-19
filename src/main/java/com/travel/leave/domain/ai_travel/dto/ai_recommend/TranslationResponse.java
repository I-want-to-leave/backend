package com.travel.leave.domain.ai_travel.dto.ai_recommend;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TranslationResponse {

    @JsonProperty("data")
    private DataNode data;

    @Data
    public static class DataNode {
        @JsonProperty("translations")
        private List<TranslationNode> translations;
    }

    @Data
    public static class TranslationNode {
        @JsonProperty("translatedText")
        private String translatedText;
    }
}