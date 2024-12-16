package com.travel.leave.subdomain.postpreparation.entity;

import com.travel.leave.subdomain.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_preparation")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostPreparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_preparation_code")
    private Long code;

    @Column(name = "preparation_name", nullable = false)
    private String name;

    @Column(name = "preparation_quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "post_code", nullable = false)
    private Post post;
}
