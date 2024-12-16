package com.travel.leave.ai_travel.service.gpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.travel.leave.ai_travel.service.trip_enum.UNSPLASH_ENUM;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UnsplashService {

    private final WebClient unsplashWebClient;

    @Value("${unsplash.api.key}")
    private String unsplashApiKey;

    public CompletableFuture<String> fetchImageUrl(String keyword) {
        int randomPage = (int) (Math.random() * 10) + 1;
        return unsplashWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(UNSPLASH_ENUM.UNSPLASH_PATH.getField())
                        .queryParam(UNSPLASH_ENUM.QUERY_PARAM_QUERY.getField(), keyword)
                        .queryParam(UNSPLASH_ENUM.QUERY_PARAM_CLIENT_ID.getField(), unsplashApiKey)
                        .queryParam(UNSPLASH_ENUM.QUERY_PARAM_PER_PAGE.getField(), 1)
                        .queryParam(UNSPLASH_ENUM.QUERY_PARAM_PAGE.getField(), randomPage)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .doOnNext(response -> System.out.println("Unsplash API 사용" + response))
                .map(response -> {
                    if (response.has(UNSPLASH_ENUM.QUERY_RESULT.getField()) && !response.get(UNSPLASH_ENUM.QUERY_RESULT.getField()).isEmpty()) {
                        return response.get(UNSPLASH_ENUM.QUERY_RESULT.getField()).get(0).get(UNSPLASH_ENUM.QUERY_URLS.getField()).get(UNSPLASH_ENUM.QUERY_REGULAR.getField()).asText();
                    } else {
                        return "https://via.placeholder.com/1080x720?text=No+Image+Found";
                    }
                })
                .toFuture();
    }
}