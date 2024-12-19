package com.travel.leave.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Operation;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.Map;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().components(new Components()).info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("떠나 볼래?")
                .description("백석대학교 모자돌 캡스톤")
                .version("1.0.0");
    }

    @Bean
    public OpenApiCustomizer tripApiCustomizer() {
        return openApi -> openApi.getPaths().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("/api/trip"))
                .forEach(entry -> entry.getValue().readOperations().forEach(this::applyDefaultResponses));
    }

    private void applyDefaultResponses(Operation operation) {
        Map<String, String> defaultResponses = Map.of(
                ApiResponseStatus.SUCCESS.getCode(), ApiResponseStatus.SUCCESS.getDescription(),
                ApiResponseStatus.BAD_REQUEST.getCode(), ApiResponseStatus.BAD_REQUEST.getDescription(),
                ApiResponseStatus.NOT_FOUND.getCode(), ApiResponseStatus.NOT_FOUND.getDescription(),
                ApiResponseStatus.SERVER_ERROR.getCode(), ApiResponseStatus.SERVER_ERROR.getDescription()
        );

        defaultResponses.forEach((code, description) -> {
            if (operation.getResponses().get(code) == null) {
                operation.getResponses().addApiResponse(code, createApiResponse(description));
            }
        });
    }

    private ApiResponse createApiResponse(String description) {
        ApiResponse response = new ApiResponse();
        response.setDescription(description);
        return response;
    }
}