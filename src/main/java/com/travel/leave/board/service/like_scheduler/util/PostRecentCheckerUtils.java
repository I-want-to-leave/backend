package com.travel.leave.board.service.like_scheduler.util;

import com.travel.leave.board.repository.post.PostRepository;
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