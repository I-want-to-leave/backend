package com.travel.leave.travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "travel_expenses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TravelExpenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_expenses_code")
    private Long code;

    @Column(name = "travel_expenses_name")
    private String name;

    @Column(name = "travel_expenses_amount")
    private Long amount;

    @Column(name = "travel_location_code")
    private Long travelLocationCode;

    @Column(name = "user_code")
    private Long userCode;
}
