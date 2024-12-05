package com.travel.leave.travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;
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
    private Timestamp startDate;

    @Column(name = "travel_end_date")
    private Timestamp endDate;
}