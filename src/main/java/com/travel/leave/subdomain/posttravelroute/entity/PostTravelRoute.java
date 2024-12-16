package com.travel.leave.subdomain.posttravelroute.entity;

import com.travel.leave.subdomain.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "post_travel_route")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostTravelRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_travel_route_code")
    private Long code;

    @Column(name = "place_name", nullable = false)
    private String placeName;

    @Column(name = "start_at", nullable = false)
    private Timestamp startAt;

    @Column(name = "end_at", nullable = false)
    private Timestamp endAt;

    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 10, scale = 6)
    private BigDecimal longitude;

    @ManyToOne
    @JoinColumn(name = "post_code", nullable = false)
    private Post post;
}