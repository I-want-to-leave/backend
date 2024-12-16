package com.travel.leave.domain.board.dto.response.postdetail;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDTO {
    private Long postCode;
    private String postTitle;
    private String postContent;
    private Date startDate;
    private Date endDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long views;
    private Long likeCount;
    private List<PostImageDTO> images;
    private List<PostCommentDTO> comments;
    private List<PostPreparationDTO> preparations;
    private List<PostTravelRouteDTO> travelRoutes;
}