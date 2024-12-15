package com.travel.leave.board.validator;

import com.travel.leave.board.entity.PostComment;
import com.travel.leave.board.repository.comment.PostCommentRepository;
import com.travel.leave.board.service.enums.BOARD_EX_MSG;
import com.travel.leave.exception.BadReqeust.InvalidPostCommentRelationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCommentValidator {

    private final PostCommentRepository postCommentRepository;

    public PostComment validateOwnership(Long postCode, Long commentCode, Long userCode){
        PostComment comment = postCommentRepository.findActiveCommentById(commentCode)
                .orElseThrow(() -> new EntityNotFoundException(BOARD_EX_MSG.COMMENT_NOT_FOUND.getMessage()));

        if (!comment.getPost().getPostCode().equals(postCode)) {
            throw new InvalidPostCommentRelationException(BOARD_EX_MSG.INVALID_POST_COMMENT_RELATION.getMessage());
        }

        if (!comment.getUserCode().equals(userCode)) {
            throw new AccessDeniedException(BOARD_EX_MSG.COMMENT_ACCESS_DENIED.getMessage());
        }

        return comment;
    }
}