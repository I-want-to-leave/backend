package com.travel.leave.login.jwt.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    private static final Logger log = LoggerFactory.getLogger(JWTUtil.class);
    private static SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret){    //생성자가 yml 의 key를 받아와서 시크릿 키를 만들었다.
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String createJWT(Long code, String username, String nickName, String role, Long expiredMs){
        return Jwts.builder()
                .claim("code", code)
                .claim("username", username)
                .claim("nickname", nickName)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public static Long getCode(String jwtToken){
        return getInfo(jwtToken).get("code", Long.class);
    }

    public static String getUsername(String jwtToken) {
        return getInfo(jwtToken).get("username", String.class);
    }

    public static String getNickname(String jwtToken) {
        return getInfo(jwtToken).get("nickname", String.class);
    }

    public static String getRole(String jwtToken) {
        return getInfo(jwtToken).get("role", String.class);
    }

    public Boolean isExpired(String token){
        try{
            Boolean test = getInfo(token).getExpiration().before(new Date());
            return test;
        } catch (ExpiredJwtException e){
            return true;
        }


    }

    private static Claims getInfo(String jwtToken){
        jwtToken = jwtToken.substring(7);
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwtToken).getPayload();
    }
}
