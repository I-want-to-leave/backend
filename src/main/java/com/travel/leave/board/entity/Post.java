package com.travel.leave.board.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
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

    @Column(name = "post_start_date", nullable = false)
    private Date startDate;

    @Column(name = "post_end_date", nullable = false)
    private Date endDate;

    @Column(name = "post_created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "post_updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(name = "post_deleted_at")
    private Timestamp deletedAt;

    @Column(name = "post_views", nullable = false)
    private Long views;

    @Column(name = "user_code", nullable = false)
    private Long userCode;

    @Column(name = "travel_code", nullable = false)
    private Long travelCode;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostComment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostImage> images;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostLike> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostPreparation> preparations;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostTravelRoute> travelRoutes;

    public void deactivate(Timestamp timestamp) {
        this.deletedAt = timestamp;
    }
}