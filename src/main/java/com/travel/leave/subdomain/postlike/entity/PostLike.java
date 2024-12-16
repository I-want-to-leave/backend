package com.travel.leave.subdomain.postlike.entity;

import com.travel.leave.subdomain.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_like")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_code")
    private Long code;

    @ManyToOne
    @JoinColumn(name = "post_code", nullable = false)
    private Post post;

    @Column(name = "user_code", nullable = false)
    private Long userCode;
}
