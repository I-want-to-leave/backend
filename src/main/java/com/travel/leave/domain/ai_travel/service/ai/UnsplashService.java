package com.travel.leave.domain.ai_travel.service.ai;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class UnsplashService {

    private final WebClient unsplashWebClient;

    @Value("${unsplash.api.key}")
    private String unsplashApiKey;

    public CompletableFuture<ResponseEntity<String>> fetchImageUrl(String keyword) {
        int randomPage = (int) (Math.random() * 10) + 1;
        return unsplashWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/photos")
                        .queryParam("query", keyword)
                        .queryParam("client_id", unsplashApiKey)
                        .queryParam("per_page", 1)
                        .queryParam("page", randomPage)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .doOnNext(response -> System.out.println("Unsplash API Response: " + response))
                .map(response -> {
                    if (response.has("results") && !response.get("results").isEmpty()) {
                        return response.get("results").get(0).get("urls").get("regular").asText();
                    } else {
                        return "https://via.placeholder.com/1080x720?text=No+Image+Found";
                    }
                })
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
}