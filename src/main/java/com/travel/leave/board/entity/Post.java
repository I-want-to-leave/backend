package com.travel.leave.board.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_code")
    private Long postCode;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_content", nullable = false)
    private String postContent;

    @Column(name = "post_created_at", nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "post_updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(name = "post_deleted_at")
    private Timestamp deletedAt;

    @Column(name = "post_views")
    private Long views;

    @Column(name = "user_code", nullable = false)
    private Long userCode;

    @OneToMany(mappedBy = "post")
    private List<PostLike> likes = new ArrayList<>();

    public void updatePost(String title, String content) {
        this.postTitle = title;
        this.postContent = content;
    }

    public void deactivate(Timestamp timestamp) {
        this.deletedAt = timestamp;
    }
}
