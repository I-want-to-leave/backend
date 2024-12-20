package com.travel.leave.travel.service.gpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.travel.leave.travel.dto.ai.*;
import com.travel.leave.travel.dto.gpt.GPTRequest;
import com.travel.leave.travel.dto.gpt.GPTResponse;
import com.travel.leave.travel.mapper.GPTResponseMapper;
import com.travel.leave.travel.mapper.PromptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class GPTService {

    @Value("${gpt.model}")
    private String model;

    private final WebClient gptWebClient;

    public CompletableFuture<ResponseEntity<GPTResponse>> getRecommendation(TripRequestDTO tripRequestDTO) {
        String prompt = PromptMapper.generatePrompt(tripRequestDTO);
        GPTRequest gptRequest = new GPTRequest(model, prompt);

        return gptWebClient.post()
                .bodyValue(gptRequest)
                .retrieve()
                .bodyToMono(GPTResponse.class)
                .doOnNext(response -> System.out.println("GPT 응답: " + response))
                .toFuture()
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof TimeoutException) {
                        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(null);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                    }
                });
    }

    public List<RecommendedItemDTO> extractRecommendedItems(GPTResponse gptResponse) {
        JsonNode rootNode = GPTResponseMapper.parseResponseContent(gptResponse);
        return GPTResponseMapper.toRecommendedItems(rootNode);
    }

    public List<List<LatLngDTO>> extractDailyRoutes(GPTResponse gptResponse) {
        JsonNode rootNode = GPTResponseMapper.parseResponseContent(gptResponse);
        return GPTResponseMapper.toDailyRoutes(rootNode);
    }
}