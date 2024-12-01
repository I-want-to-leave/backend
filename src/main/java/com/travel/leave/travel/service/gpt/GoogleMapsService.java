package com.travel.leave.travel.service.gpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.travel.leave.travel.TransportType;
import com.travel.leave.travel.dto.ai.LatLngDTO;
import com.travel.leave.travel.mapper.GoogleMapsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoogleMapsService {

    private final WebClient googleGeocodingWebClient;

    @Value("${google.maps.api-key}")
    private String apikey;

    // 특정 장소의 좌표 데이터를 가져온다.
    public CompletableFuture<JsonNode> fetchCoordinatesResponse(String placeName) {
        return googleGeocodingWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("address", placeName)
                        .queryParam("key", apikey)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .doOnNext(response -> System.out.println("Google Map " + placeName + ": " + response))
                .toFuture();
    }

    // 응답 데이터를 LatLngDTO 매핑
    private CompletableFuture<LatLngDTO> mapToLatLngDTO(JsonNode response, String placeName, TransportType transportType) {
        return CompletableFuture.supplyAsync(() -> GoogleMapsMapper.ofLatLngDTO(response, placeName, transportType));
    }

    // 매핑된 데이터를 가져오고 전체 흐름을 제어
    public CompletableFuture<LatLngDTO> getCoordinates(String placeName, TransportType transportType) {
        return fetchCoordinatesResponse(placeName)
                .thenCompose(response -> mapToLatLngDTO(response, placeName, transportType))
                .exceptionally(ex -> null);
    }

    // 일별 경로 처리
    private CompletableFuture<List<LatLngDTO>> processDayRoutes(List<LatLngDTO> gptDayRoutes) {
        List<CompletableFuture<LatLngDTO>> futures = gptDayRoutes.stream()
                .map(routeStep -> getCoordinates(routeStep.getPlaceName(), routeStep.getTransportType()))
                .toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }

    // 전체 경로 처리
    public CompletableFuture<List<List<LatLngDTO>>> processDailyRoutes(List<List<LatLngDTO>> gptDailyRoutes) {
        List<CompletableFuture<List<LatLngDTO>>> futures = gptDailyRoutes.stream()
                .map(this::processDayRoutes)
                .toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .filter(dayCoordinates -> !dayCoordinates.isEmpty())
                        .collect(Collectors.toList()));
    }
}