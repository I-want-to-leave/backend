package com.travel.leave.board.repository.post_iamge;

import com.travel.leave.board.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageOrderRepository extends JpaRepository<PostImage, Long> {

    @Modifying
    @Query("UPDATE PostImage pi SET pi.order = pi.order - 1 WHERE pi.post.postCode = :postCode AND pi.order > :deletedOrder")
    void updateOrderAfterDelete(@Param("postCode") Long postCode, @Param("deletedOrder") Long deletedOrder);
    // 삭제 후, 게시글 order 필드 값 숫자 조정

    @Modifying
    @Query("UPDATE PostImage pi SET pi.filePath = :newFilePath WHERE pi.post.postCode = :postCode AND pi.filePath = :oldFilePath")
    void updateImageUrl(@Param("postCode") Long postCode, @Param("oldFilePath") String oldFilePath, @Param("newFilePath") String newFilePath);
    // 기존 파일 경로를 새로운 이미지 파일 경로로 덮어씌움

    @Modifying
    @Query("""
        UPDATE PostImage pi
        SET pi.order = CASE
            WHEN pi.order = :currentOrder THEN :newOrder
            WHEN pi.order = :newOrder THEN :currentOrder
        END
        WHERE pi.post.postCode = :postCode
        AND (pi.order = :currentOrder OR pi.order = :newOrder)
    """)
    void swapZeroImageOrder(@Param("postCode") Long postCode, @Param("currentOrder") Long currentOrder, @Param("newOrder") Long newOrder);
    // 대표이미지는 항상 0에 있으며, 이를 기존 이미지의 order 와 맞교환 하는 방식으로 대표 이미지 교체
}