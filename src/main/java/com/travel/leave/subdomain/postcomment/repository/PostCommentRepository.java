package com.travel.leave.subdomain.postcomment.repository;

import com.travel.leave.domain.board.dto.response.postdetail.PostCommentDTO;
import com.travel.leave.subdomain.post.entity.Post;
import com.travel.leave.subdomain.postcomment.entity.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    @Query("""
    SELECT new com.travel.leave.domain.board.dto.response.postdetail.PostCommentDTO(
        pc.code,
        pc.content,
        pc.createdAt,
        pc.post.postCode,
        pc.nickname
    )
    FROM PostComment pc
    WHERE pc.post.postCode = :postCode
      AND pc.deletedAt IS NULL
    """)
    List<PostCommentDTO> findCommentsByPostCode(@Param("postCode") Long postCode);

    @Query("""
    SELECT new com.travel.leave.domain.board.dto.response.postdetail.PostCommentDTO(
        pc.code,
        pc.content,
        pc.createdAt,
        pc.post.postCode,
        pc.nickname
    )
    FROM PostComment pc
    WHERE pc.code = :commentCode
      AND pc.deletedAt IS NULL
    """)
    PostCommentDTO findCommentDTOById(@Param("commentCode") Long commentCode);

    @Query("SELECT pc FROM PostComment pc WHERE pc.code = :commentCode AND pc.deletedAt IS NULL")
    Optional<PostComment> findActiveCommentById(@Param("commentCode") Long commentCode);
    // 활성화 된 댓글 찾는 SQL -> 활성화 되지 않은 댓글 찾을 가능성을 없앰

    Page<PostComment> findAllByDeletedAtBefore(Timestamp timestamp, Pageable pageable);
}