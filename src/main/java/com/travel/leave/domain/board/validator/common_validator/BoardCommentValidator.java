package com.travel.leave.domain.board.validator.common_validator;

import com.travel.leave.domain.board.exception.enums.DefaultExceptionMessages;
import com.travel.leave.subdomain.postcomment.entity.PostComment;
import com.travel.leave.subdomain.postcomment.repository.PostCommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardCommentValidator {

    private final PostCommentRepository postCommentRepository;

    public void validateOwnership(Long commentCode, Long userCode){
        PostComment comment = postCommentRepository.findActiveCommentById(commentCode)
                .orElseThrow(() -> new EntityNotFoundException(DefaultExceptionMessages.COMMENT_NOT_FOUND.getMessage()));

        if (!comment.getUserCode().equals(userCode)) {
            throw new AccessDeniedException(DefaultExceptionMessages.COMMENT_ACCESS_DENIED.getMessage());
        }
    }
}