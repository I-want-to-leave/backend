package com.travel.leave.domain.board.service.scheduler;

import com.travel.leave.subdomain.post.entity.Post;
import com.travel.leave.subdomain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostCleanupScheduler {

    private final PostRepository postRepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
    public void cleanUpDeletedPosts() {
        Timestamp threeDaysAgo = Timestamp.valueOf(LocalDateTime.now().minusDays(3));
        PageRequest pageRequest = PageRequest.of(0, 100);

        Page<Post> postsToDelete;
        do {
            postsToDelete = postRepository.findAllByDeletedAtBefore(threeDaysAgo, pageRequest);
            if (!postsToDelete.isEmpty()) {
                postRepository.deleteAllInBatch(postsToDelete.getContent());
                log.info("3일 전에 삭제 된 게시글을 100개 씩 차례로 처리 중 입니다. 처리 갯수: {}", postsToDelete.getNumberOfElements());
            }
        } while (postsToDelete.hasNext());
    }
}