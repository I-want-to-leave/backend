package com.travel.leave.board.repository.preparation;

import com.travel.leave.board.dto.response.postdetail.PostPreparationDTO;
import com.travel.leave.board.entity.PostPreparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostPreparationRepository extends JpaRepository<PostPreparation, Long> {

    @Query("""
        SELECT new com.travel.leave.board.dto.response.postdetail.PostPreparationDTO(
            pp.name,
            pp.quantity
        )
        FROM PostPreparation pp
        WHERE pp.post.postCode = :postCode
    """)
    List<PostPreparationDTO> findPreparationsByPostCode(@Param("postCode") Long postCode);
}