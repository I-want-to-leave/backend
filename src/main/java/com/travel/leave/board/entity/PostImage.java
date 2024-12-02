package com.travel.leave.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "POST_IMAGE")
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @Column(name = "POST_IMAGE_POSITION", nullable = false)
    private String position;

    @Column(name = "POST_IAMGE_URL", nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "post_code", nullable = false)
    private Post post;
}
