package com.travel.leave.Board.repository;

import com.travel.leave.Board.entity.Post;
import com.travel.leave.Board.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("SELECT pl.userCode FROM PostLike pl WHERE pl.post.postCode = :postCode")
    List<Long> findUserCodesByPostCode(@Param("postCode") Long postCode);

    @Modifying
    @Query("DELETE FROM PostLike pl WHERE pl.post = :post AND pl.userCode = :userCode")
    void deleteByPostAndUserCode(@Param("post") Post post, @Param("userCode") Long userCode);
    // PostLike 사용자 코드인 행을 삭제
}