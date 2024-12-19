package com.travel.leave.domain.board.dto.response.postdetail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "게시글 상세 응답 DTO")
public class PostResponseDTO {
    @Schema(description = "게시글 코드", example = "123")
    private Long postCode;

    @Schema(description = "게시글 제목", example = "서울 여행 후기")
    private String postTitle;

    @Schema(description = "게시글 내용", example = "서울에서의 3일 여행 이야기입니다.")
    private String postContent;

    @Schema(description = "여행 시작 날짜", example = "2024-12-19")
    private Date startDate;

    @Schema(description = "여행 종료 날짜", example = "2024-12-22")
    private Date endDate;

    @Schema(description = "게시글 생성 시간", example = "2024-12-19T18:30:00.000Z")
    private Timestamp createdAt;

    @Schema(description = "게시글 수정 시간", example = "2024-12-20T10:00:00.000Z")
    private Timestamp updatedAt;

    @Schema(description = "조회수", example = "150")
    private Long views;

    @Schema(description = "좋아요 개수", example = "30")
    private Long likeCount;

    @Schema(description = "게시글 이미지 목록")
    private List<PostImageDTO> images;

    @Schema(description = "게시글 댓글 목록")
    private List<PostCommentDTO> comments;

    @Schema(description = "여행 준비물 목록")
    private List<PostPreparationDTO> preparations;

    @Schema(description = "여행 경로 목록")
    private List<PostTravelRouteDTO> travelRoutes;
}
