package com.travel.leave.login.entity;

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
@Table(name = "user_travel")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTravel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_travel_code")
    private Long code;

    @Column(name = "travel_code")
    private Long travelCode;

    @Column(name = "user_code")
    private Long userCode;
}