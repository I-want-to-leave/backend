package com.travel.leave.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_like")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_code")
    private Long code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_code", nullable = false)
    private Post post;

    @Column(name = "user_code")
    private Long userCode;
}
