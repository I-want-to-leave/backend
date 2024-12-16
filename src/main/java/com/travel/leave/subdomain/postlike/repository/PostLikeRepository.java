package com.travel.leave.subdomain.postlike.repository;

import com.travel.leave.subdomain.postlike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    // 게시물별 좋아요 수 카운트
    @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.post.postCode = :postCode")
    Long countLikesByPostCode(@Param("postCode") Long postCode);

    // 특정 게시물과 사용자의 좋아요 데이터 삭제
    @Modifying
    @Query("DELETE FROM PostLike pl WHERE pl.post.postCode = :postCode AND pl.userCode = :userCode")
    void deleteByPostCodeAndUserCode(@Param("postCode") Long postCode, @Param("userCode") Long userCode);

    // 특정 게시물의 모든 좋아요 데이터 조회
    @Query("SELECT pl FROM PostLike pl WHERE pl.post.postCode = :postCode")
    List<PostLike> findAllByPostCode(@Param("postCode") Long postCode);
}
