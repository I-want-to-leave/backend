package com.travel.leave.subdomain.travelpreparation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "travel_preparation")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPreparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_preparation_code")
    private Long code;

    @Column(name = "travel_preparation_name")
    private String name;

    @Column(name = "travel_preparation_quantity")
    private Integer quantity;

    @Column(name = "travel_preparation_is_deleted")
    private Boolean isDeleted;

    @Column(name = "travel_code")
    private Long travelCode;

    public static TravelPreparation of(Long code, String name, Integer quantity, Boolean isDeleted, Long travelCode) {
        return new TravelPreparation(code, name, quantity, isDeleted, travelCode);
    }
}