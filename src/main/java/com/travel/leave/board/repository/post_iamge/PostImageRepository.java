package com.travel.leave.board.repository.post_iamge;

import com.travel.leave.board.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    @Query("SELECT pi FROM PostImage pi WHERE pi.post.postCode = :postCode ORDER BY pi.order DESC")
    List<PostImage> findImagesByPostCode(@Param("postCode") Long postCode);

    @Query("SELECT MAX(pi.order) FROM PostImage pi WHERE pi.post.postCode = :postCode")
    Optional<Long> findMaxOrderByPostCode(@Param("postCode") Long postCode);
    // order 의 가장 큰 값, 이미지 추가 시, order 값 조정 작업을 위함

    @Query("SELECT pi FROM PostImage pi WHERE pi.post.postCode = :postCode AND pi.filePath = :filePath")
    Optional<PostImage> findFilePath(@Param("postCode") Long postCode, @Param("filePath") String filePath);
    // 삭제 전, 해당 이미지 파일의 order 값을 알아내기 위함

    @Modifying
    @Query("DELETE FROM PostImage pi WHERE pi.post.postCode = :postCode AND pi.filePath = :filePath")
    void deleteFilePath(@Param("postCode") Long postCode, @Param("filePath") String filePath);
}