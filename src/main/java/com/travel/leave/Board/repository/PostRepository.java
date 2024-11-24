package com.travel.leave.Board.repository;

import com.travel.leave.Board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Query("UPDATE Post p SET p.views = p.views + 1 WHERE p.postCode = :postCode")
    void incrementViews(@Param("postCode") Long postCode);
    // 행 락, 베타적 락으로 동시성 제어

    @Query("SELECT p, COUNT(pl) as likeCount, pc " +
            "FROM Post p " +
            "LEFT JOIN p.likes pl " +
            "LEFT JOIN PostComment pc ON pc.postCode = p.postCode AND pc.deletedAt IS NULL " +
            "WHERE p.postCode = :postCode " +
            "GROUP BY p, pc")
    List<Object[]> findPostWithLikeCountAndComments(@Param("postCode") Long postCode);
    // 조인작업을 통해 트랜잭션 1번을 최소화

    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL AND p.postCode = :postCode")
    Optional<Post> findActivePostById(@Param("postCode") Long postCode);
    // 글로벌 필터링을 적용

    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL ORDER BY p.createdAt DESC")
    Page<Post> findAllActivePosts(Pageable pageable);
    // 논리 삭제된 게시글 제외한 조회
}
