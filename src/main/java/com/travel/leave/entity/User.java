package com.travel.leave.entity;

import com.travel.leave.join.dto.JoinRequestDTO;
import com.travel.leave.login.oauth.service.response.OAuth2Response;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "user")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private Long code;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "tel_num")
    private String telNum;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    private User(OAuth2Response oAuth2Response) {
        this.username = oAuth2Response.getUserId();
        this.nickname = oAuth2Response.getNickname();
        this.email = oAuth2Response.getEmail();
        this.role = UserRole.ROLE_USER;
        this.provider = oAuth2Response.getProvider();
        this.providerId = oAuth2Response.getProviderId();
    }

    private User(JoinRequestDTO joinRequestDTO, String bCryptPassword) {
        this.username = joinRequestDTO.username();
        this.nickname = joinRequestDTO.nickname();
        this.email = joinRequestDTO.email();
        this.password = bCryptPassword;
        this.role = UserRole.ROLE_USER;
    }

    public static User ofOAuth2Response(OAuth2Response oAuth2Response) {
        return new User(oAuth2Response);
    }

    public static User ofJoinRequestDTO(JoinRequestDTO joinRequestDTO, String bCryptPassword) {
        return new User(joinRequestDTO, bCryptPassword);
    }
}