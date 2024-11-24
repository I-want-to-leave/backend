package com.travel.leave.Board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "post_comment_content")
    private String content;

    @Column(name = "post_created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "post_deleted_at")
    private Timestamp deletedAt;

    @Column(name = "user_code")
    private Long userCode;

    @Column(name = "post_code")
    private Long postCode;

    public void setterContent(String content) {
        this.content = content;
    }

    public void deactivate() {
        this.deletedAt = new Timestamp(System.currentTimeMillis());
    }

    public static PostComment create(Long postCode, Long userCode, String content) {
        return PostComment.builder()
                .postCode(postCode)
                .userCode(userCode)
                .content(content)
                .build();
    }
}
