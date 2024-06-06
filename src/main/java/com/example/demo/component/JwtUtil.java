package com.example.demo.component;

import com.example.demo.controller.dto.UserInfoDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key;
    private final long accessTokenExpTime;

    public JwtUtil(@Value("${jwt.secret}") String secretkey, @Value("${jwt.expiration_time}") long accessTokenExpTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);

        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("Secret key must be at least 32 bytes");
        }

        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
    }

    /**
     * Access Token 생성
     *
     * @param userInfoDTO User Information
     * @return Access Token String
     */
    public String createAccessToken(UserInfoDTO userInfoDTO) {

        return createToken(userInfoDTO, accessTokenExpTime);
    }

    /**
     * JWT 생성
     *
     * @param userInfoDTO User Information
     * @param expireTime  Expire Time
     * @return JWT String
     */
    private String createToken(UserInfoDTO userInfoDTO, long expireTime) {
        Claims claims = Jwts.claims().setSubject(userInfoDTO.getUsername());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * JWT 검증
     *
     * @param token Token
     * @return Is Valid Token
     */
    public Boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("Invalid JWT");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    /**
     * JWT Claims 추출
     *
     * @param token Access Token
     * @return JWT Claims
     */
    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * JWT 에서 Username 추출
     *
     * @param token
     * @return Username
     */
    public String getUsername(String token) {
        return parseClaims(token).get("username", String.class);
    }
}