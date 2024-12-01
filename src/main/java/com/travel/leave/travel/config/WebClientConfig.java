package com.travel.leave.travel.config;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient googleGeocodingWebClient(@Value("${google.maps.url}") String googleUrl) {
        return WebClient.builder()
                .baseUrl(googleUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .responseTimeout(Duration.ofSeconds(25))
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 25000)
                ))
                .build();
    }

    @Bean
    public WebClient gptWebClient(
            @Value("${gpt.api.url}") String gptApiUrl,
            @Value("${gpt.api.key}") String gptApiKey) {
        return WebClient.builder()
                .baseUrl(gptApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + gptApiKey)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .responseTimeout(Duration.ofSeconds(25))
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 25000)
                ))
                .build();
    }
}