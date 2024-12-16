package com.travel.leave.subdomain.postcomment.entity;

import com.travel.leave.subdomain.post.entity.Post;
import jakarta.persistence.*;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "post_comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_code")
    private Long code;

    @Column(name = "post_comment_content", nullable = false)
    private String content;

    @Column(name = "post_created_at", nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "post_deleted_at")
    private Timestamp deletedAt;

    @Column(name = "user_code", nullable = false)
    private Long userCode;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "post_code", nullable = false)
    private Post post;

    public void setterContent(String content) {
        this.content = content;
    }

    public void deactivate() {
        this.deletedAt = new Timestamp(System.currentTimeMillis());
    }
}