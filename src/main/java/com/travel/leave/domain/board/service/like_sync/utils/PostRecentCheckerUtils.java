package com.travel.leave.domain.board.service.like_sync.utils;

import com.travel.leave.subdomain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostRecentCheckerUtils {

    private final PostRepository postRepository;

    public boolean isPostOld(Long postCode) {
        LocalDateTime postCreatedDate = postRepository.findCreatedDateByPostCode(postCode);
        return !postCreatedDate.isAfter(LocalDateTime.now().minusDays(3));
    } // true 면 3일 전보다 오래 됨, false 면 3일 이내에 작성 됨
}