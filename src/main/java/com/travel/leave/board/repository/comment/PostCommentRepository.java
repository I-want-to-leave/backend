package com.travel.leave.board.repository.comment;

import com.travel.leave.board.dto.response.postdetail.PostCommentDTO;
import com.travel.leave.board.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    @Query("""
    SELECT new com.travel.leave.board.dto.response.postdetail.PostCommentDTO(
        pc.code,
        pc.content,
        pc.createdAt,
        pc.userCode,
        pc.post.postCode
    )
    FROM PostComment pc
    WHERE pc.post.postCode = :postCode
      AND pc.deletedAt IS NULL
    """)
    List<PostCommentDTO> findCommentsByPostCode(@Param("postCode") Long postCode);

    @Query("SELECT pc FROM PostComment pc WHERE pc.code = :commentCode AND pc.deletedAt IS NULL")
    Optional<PostComment> findActiveCommentById(@Param("commentCode") Long commentCode);
    // 활성화 된 댓글 찾는 SQL -> 활성화 되지 않은 댓글 찾을 가능성을 없앰
}