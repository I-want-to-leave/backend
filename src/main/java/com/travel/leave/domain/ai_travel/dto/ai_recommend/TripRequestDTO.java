package com.travel.leave.domain.ai_travel.dto.ai_recommend;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripRequestDTO {

    @Schema(description = "여행 희망지역", example = "서울")
    private String startLocation;

    @Schema(description = "여행 시작 날짜", example = "yyyy-mm-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "여행 끝 날짜", example = "yyyy-mm-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Schema(description = "여행 핵심 키워드", example = "도시")
    private String keywords;

    @Schema(description = "자차 유무", example = "true")
    private boolean carOwned;
}