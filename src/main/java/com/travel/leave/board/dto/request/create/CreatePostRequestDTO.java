package com.travel.leave.board.dto.request.create;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostRequestDTO {
    private String title;
    private String content;
    private Date startDate;
    private Date endDate;
    private List<String> imagePath;
    private List<RouteRequestDTO> travelRoute;
    private List<PreparationRequestDTO> preparation;
}