package com.travel.leave.subdomain.post.repository;

import com.travel.leave.subdomain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsByTravelCode(Long travelCode);

    @Query("SELECT p.createdAt FROM Post p WHERE p.postCode = :postCode")
    LocalDateTime findCreatedDateByPostCode(@Param("postCode") Long postCode);

    @Modifying
    @Query("UPDATE Post p SET p.views = p.views + 1 WHERE p.postCode = :postCode")
    void incrementViews(@Param("postCode") Long postCode);
    // 비관적 락과 유사한 DB 내부 동시성 제어 매커니즘에 의한 동시성 제어

    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL AND p.postCode = :postCode")
    Optional<Post> findActivePostById(@Param("postCode") Long postCode);

    Page<Post> findAllByDeletedAtBefore(Timestamp timestamp, Pageable pageable);
}