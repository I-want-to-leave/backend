package com.travel.leave.board.repository;

import com.travel.leave.board.dto.ResponseDetailPostDTO;
import com.travel.leave.board.dto.ResponsePostListDTO;
import com.travel.leave.board.entity.Post;
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

    @Query("""
        SELECT new com.travel.leave.board.dto.ResponseDetailPostDTO(
            p.postCode, p.postTitle, p.postContent, p.createdAt, p.updatedAt, p.views, p.userCode,
            CAST((SELECT COUNT(l) FROM PostLike l WHERE l.post = p) AS long)
        )
        FROM Post p
        WHERE p.postCode = :postCode
    """)
    Optional<ResponseDetailPostDTO> findPostWithDetails(@Param("postCode")  Long postCode);
    // 리포지토리에서 바로 DTO 로 반환한다 -> List 같은 경우는 JPQL 에서 처리를 못한다

    @Query("""
        SELECT new com.travel.leave.board.dto.ResponsePostListDTO(
            p.postCode,
            p.postTitle,
            SUBSTRING(p.postContent, 1, 10),
            p.views
        )
        FROM Post p
        LEFT JOIN p.likes pl
        GROUP BY p
        ORDER BY COUNT(pl) DESC
    """)
    List<ResponsePostListDTO> findPostsByLikesDesc(Pageable pageable);
    // 좋아요 많은 순으로 게시물 정렬

    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL AND p.postCode = :postCode")
    Optional<Post> findActivePostById(@Param("postCode") Long postCode);
    // 글로벌 필터링을 적용

    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL ORDER BY p.createdAt DESC")
    List<Post> findAllActivePosts(Pageable pageable);
    // 논리 삭제된 게시글 제외한 조회

    @Query("SELECT p FROM Post p " +
            "WHERE p.deletedAt IS NULL " +
            "AND LOWER(p.postTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY p.createdAt DESC")
    List<Post> searchPosts(@Param("keyword") String keyword, Pageable pageable);
    // 게시글 검색 기능 처리 -> 페이징 처리로 무한 스크롤
    // 대소문자 구분 X, 키워드가 포함된 데이터를 검색
}