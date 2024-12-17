package com.travel.leave.domain.board.repository;

import com.travel.leave.domain.board.dto.response.PostListDTO;
import com.travel.leave.domain.board.board_enum.SortField;
import com.travel.leave.subdomain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface PostSortRepository extends JpaRepository<Post, Long> {

    @Query("""
        SELECT new com.travel.leave.domain.board.dto.response.PostListDTO(
            p.postCode,
            p.postTitle,
            SUBSTRING(p.postContent, 1, 10),
            p.views,
            (SELECT pi.filePath FROM PostImage pi WHERE pi.post.postCode = p.postCode AND pi.order = 0)
        )
        FROM Post p
        LEFT JOIN p.likes pl
        WHERE p.deletedAt IS NULL
        GROUP BY p.postCode
        ORDER BY
            CASE WHEN :SortField = 'likes' THEN COUNT(pl) END DESC,
            CASE WHEN :SortField = 'views' THEN p.views END DESC,
            CASE WHEN :SortField = 'createdat' THEN p.createdAt END DESC
    """)
    List<PostListDTO> findPostsBySort(@Param("SortField") SortField SortField, Pageable pageable);

    @Query("""
        SELECT new com.travel.leave.domain.board.dto.response.PostListDTO(
            p.postCode,
            p.postTitle,
            SUBSTRING(p.postContent, 1, 10),
            p.views,
            (SELECT pi.filePath FROM PostImage pi WHERE pi.post.postCode = p.postCode AND pi.order = 0)
        )
        FROM Post p
        WHERE p.deletedAt IS NULL
        AND LOWER(p.postTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
        ORDER BY p.createdAt DESC
    """)
    List<PostListDTO> searchPosts(@Param("keyword") String keyword, Pageable pageable);
}
