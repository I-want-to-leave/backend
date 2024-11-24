package com.travel.leave.board.repository;

import com.travel.leave.board.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    @Query("SELECT pc FROM PostComment pc WHERE pc.code = :commentCode AND pc.deletedAt IS NULL")
    Optional<PostComment> findActiveCommentById(@Param("commentCode") Long commentCode);

    @Query("SELECT c FROM PostComment c WHERE c.postCode = :postCode")
    List<PostComment> findCommentsByPostCode(@Param("postCode") Long postCode);
}