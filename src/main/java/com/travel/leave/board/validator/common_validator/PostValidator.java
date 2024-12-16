package com.travel.leave.board.validator.common_validator;

import com.travel.leave.board.entity.Post;
import com.travel.leave.board.repository.post.PostRepository;
import com.travel.leave.board.service.enums.BOARD_EX_MSG;
import com.travel.leave.exception.BadReqeust.PostAlreadySharedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidator {

    private final PostRepository postRepository;

    public void validateUniqueTravelCode(Long travelCode) {
        if (postRepository.existsByTravelCode(travelCode)) {
            throw new PostAlreadySharedException(BOARD_EX_MSG.POST_ALREADY_SHARED.getMessage());
        }
    }

    public Post validateActivePost(Long postCode) {
        return postRepository.findActivePostById(postCode)
                .orElseThrow(() -> new EntityNotFoundException(BOARD_EX_MSG.POST_NOT_FOUND.getMessage()));
    }

    public Post returnValidatePostMaster(Long postCode, Long userCode) {
        Post post = postRepository.findActivePostById(postCode)
                .orElseThrow(() -> new EntityNotFoundException(BOARD_EX_MSG.POST_NOT_FOUND.getMessage()));
        if (!post.getUserCode().equals(userCode)) {
            throw new AccessDeniedException(BOARD_EX_MSG.COMMENT_ACCESS_DENIED.getMessage());
        }
        return post;
    } // 게시글 논리 삭제 시, 활동성 검증과 반환으로 곧 바로 비활성화 시키기 위해서 엔티티 반환시킴

    public void validatePostMaster(Long postCode, Long userCode) {
        Post post = postRepository.findActivePostById(postCode)
                .orElseThrow(() -> new EntityNotFoundException(BOARD_EX_MSG.POST_NOT_FOUND.getMessage()));
        if (!post.getUserCode().equals(userCode)) {
            throw new AccessDeniedException(BOARD_EX_MSG.COMMENT_ACCESS_DENIED.getMessage());
        }
    }
}