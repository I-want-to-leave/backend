package com.travel.leave.domain.board.validator.common_validator;

import com.travel.leave.subdomain.post.entity.Post;
import com.travel.leave.subdomain.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardValidator {

    private final PostRepository postRepository;

    public Post validateActivePost(Long postCode) {
        return postRepository.findActivePostById(postCode)
                .orElseThrow(() -> new EntityNotFoundException(ValidatorExceptionMessage.POST_NOT_FOUND.getMessage()));
    }

    public void validatePostMaster(Long postCode, Long userCode) {
        Post post = validateActivePost(postCode);
        if (!post.getUserCode().equals(userCode)) {
            throw new AccessDeniedException(ValidatorExceptionMessage.POST_CONTROL_ACCESS_DENIED.getMessage());
        }
    }
}