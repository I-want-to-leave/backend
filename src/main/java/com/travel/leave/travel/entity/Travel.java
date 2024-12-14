package com.travel.leave.travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "travel")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_code")
    private Long code;

    @Column(name = "travel_name")
    private String name;

    @Column(name = "travel_content")
    private String content;

    @Column(name = "travel_created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "travel_deleted_at")
    private Timestamp deletedAt;

    @Column(name = "travel_start_date")
    private LocalDate startDate;

    @Column(name = "travel_end_date")
    private LocalDate endDate;

    @Column(name = "travel_image_url")
    private String imageUrl;

    public static Travel of(Long code, String name, String content, LocalDate startDate, LocalDate endDate, Timestamp createdAt, Timestamp deletedAt, String imageUrl) {
        return new Travel(code, name, content, createdAt, deletedAt, startDate, endDate, imageUrl);
    }
}