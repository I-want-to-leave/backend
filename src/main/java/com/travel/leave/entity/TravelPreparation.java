package com.travel.leave.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "travel_preparation")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TravelPreparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_preparation_code")
    private Long code;

    @Column(name = "travel_preparation_name")
    private String name;

    @Column(name = "travel_preparation_content")
    private String content;

    @Column(name = "travel_preparation_quantity")
    private Integer quantity;

    @Column(name = "travel_location_code")
    private Long locationCode;

    @Column(name = "user_code")
    private Long userCode;
}
