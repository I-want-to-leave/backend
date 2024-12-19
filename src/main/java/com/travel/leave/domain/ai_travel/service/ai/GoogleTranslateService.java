package com.travel.leave.domain.ai_travel.service.ai;

import com.travel.leave.domain.ai_travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.domain.ai_travel.dto.ai_recommend.TranslationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class GoogleTranslateService {

    private final WebClient googleTranslationWebClient;

    @Value("${google.translation.api-key}")
    private String apiKey;

    public CompletableFuture<ResponseEntity<String>> translateText(String text, String sourceLang, String targetLang) {
        return googleTranslationWebClient.post()
                .uri(uriBuilder -> uriBuilder.queryParam("key", apiKey).build())
                .bodyValue(Map.of(
                        "q", text,
                        "source", sourceLang,
                        "target", targetLang,
                        "format", "text"
                ))
                .retrieve()
                .bodyToMono(TranslationResponse.class)
                .map(response -> response.getData().getTranslations().get(0).getTranslatedText())
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

    public CompletableFuture<RecommendDTO> translateRecommendDTO(RecommendDTO recommendDTO) {

        CompletableFuture<ResponseEntity<String>> translatedTripName = translateText(recommendDTO.getTripName(), "en", "ko");
        CompletableFuture<ResponseEntity<String>> translatedTripExplanation = translateText(recommendDTO.getTripExplanation(), "en", "ko");

        List<CompletableFuture<Void>> itemTranslations = recommendDTO.getRecommendedItems().stream()
                .map(item -> translateText(item.getItemName(), "en", "ko")
                        .thenAccept(response -> item.setItemName(response.getBody())))
                .toList();

        List<CompletableFuture<Void>> stepTranslations = recommendDTO.getDailyRoutes().stream()
                .flatMap(List::stream)
                .map(step -> translateText(step.getPlaceName(), "en", "ko")
                        .thenAccept(response -> step.setPlaceName(response.getBody())))
                .toList();

        return CompletableFuture.allOf(
                translatedTripName,
                translatedTripExplanation,
                CompletableFuture.allOf(itemTranslations.toArray(new CompletableFuture[0])),
                CompletableFuture.allOf(stepTranslations.toArray(new CompletableFuture[0]))
        ).thenApply(v -> {
            recommendDTO.setTripName(translatedTripName.join().getBody());
            recommendDTO.setTripExplanation(translatedTripExplanation.join().getBody());
            return recommendDTO;
        });
    }
}