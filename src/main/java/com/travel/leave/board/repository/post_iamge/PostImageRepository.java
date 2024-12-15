package com.travel.leave.board.repository.post_iamge;

import com.travel.leave.board.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    @Modifying
    @Query("DELETE FROM PostImage pi WHERE pi.post.postCode = :postCode")
    void deleteByPostCode(@Param("postCode") Long postCode);

    @Query("SELECT pi FROM PostImage pi WHERE pi.post.postCode = :postCode ORDER BY pi.order DESC")
    List<PostImage> findImagesByPostCode(@Param("postCode") Long postCode);
}