package com.travel.leave.board.validator;

import com.travel.leave.board.entity.PostComment;
import com.travel.leave.board.repository.comment.PostCommentRepository;
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
                .orElseThrow(() -> new EntityNotFoundException(BoardExpMSG.COMMENT_NOT_FOUND.getMessage()));

        if (!comment.getPost().getPostCode().equals(postCode)) {
            throw new InvalidPostCommentRelationException(BoardExpMSG.INVALID_POST_COMMENT_RELATION.getMessage());
        }

        if (!comment.getUserCode().equals(userCode)) {
            throw new AccessDeniedException(BoardExpMSG.COMMENT_ACCESS_DENIED.getMessage());
        }

        return comment;
    }
}