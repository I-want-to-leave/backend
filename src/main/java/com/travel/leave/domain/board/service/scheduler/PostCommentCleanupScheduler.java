package com.travel.leave.domain.board.service.scheduler;

import com.travel.leave.subdomain.postcomment.entity.PostComment;
import com.travel.leave.subdomain.postcomment.repository.PostCommentRepository;
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
public class PostCommentCleanupScheduler {

    private final PostCommentRepository postCommentRepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 실행
    public void cleanUpDeletedPostComments() {
        Timestamp threeDaysAgo = Timestamp.valueOf(LocalDateTime.now().minusDays(3));
        PageRequest pageRequest = PageRequest.of(0, 100);

        Page<PostComment> commentsToDelete;

        do {
            commentsToDelete = postCommentRepository.findAllByDeletedAtBefore(threeDaysAgo, pageRequest);

            if (!commentsToDelete.isEmpty()) {
                postCommentRepository.deleteAllInBatch(commentsToDelete.getContent());
                log.info("3일 전 댓글들을 100개 씩 차례로 처리하였습니다 처리 갯수: {}", commentsToDelete.getNumberOfElements());
            }

        } while (commentsToDelete.hasNext());
    }
}
