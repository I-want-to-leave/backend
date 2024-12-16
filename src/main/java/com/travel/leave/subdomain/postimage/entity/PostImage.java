package com.travel.leave.subdomain.postimage.entity;

import com.travel.leave.subdomain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "POST_IMAGE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_image_code")
    private Long code;

    @Column(name = "POST_IMAGE_PATH", nullable = false)
    private String filePath;

    @Column(name = "post_image_order", nullable = false)
    private Long order;

    @ManyToOne
    @JoinColumn(name = "post_code", nullable = false)
    private Post post;
}