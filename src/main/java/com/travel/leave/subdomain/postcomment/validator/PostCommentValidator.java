package com.travel.leave.subdomain.postcomment.validator;

import com.travel.leave.exception.BadReqeust.InvalidPostCommentRelationException;
import com.travel.leave.subdomain.postcomment.entity.PostComment;
import com.travel.leave.subdomain.postcomment.repository.PostCommentRepository;
import com.travel.leave.subdomain.postcomment.service.CommentExceptionMessage;
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
                .orElseThrow(() -> new EntityNotFoundException(CommentExceptionMessage.COMMENT_NOT_FOUND.getMessage()));

        if (!comment.getPost().getPostCode().equals(postCode)) {
            throw new InvalidPostCommentRelationException(CommentExceptionMessage.INVALID_POST_COMMENT_RELATION.getMessage());
        }

        if (!comment.getUserCode().equals(userCode)) {
            throw new AccessDeniedException(CommentExceptionMessage.COMMENT_ACCESS_DENIED.getMessage());
        }

        return comment;
    }
}