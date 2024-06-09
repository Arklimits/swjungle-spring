package com.example.demo.component;

import com.example.demo.controller.dto.auth.AuthResponse;
import com.example.demo.controller.dto.user.UserInfoDTO;
import com.example.demo.exception.ExpiredJwtTokenException;
import com.example.demo.exception.InvalidJwtTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key;
    private final long accessTokenExpTime;

    /**
     * Secret Key 및 Expiration Time 설정
     *
     * @param secretkey          Secret Key
     * @param accessTokenExpTime Expiration Time
     */
    public JwtUtil(@Value("${jwt.secret}") String secretkey, @Value("${jwt.expiration_time}") long accessTokenExpTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);

        if (keyBytes.length < 32)
            throw new IllegalArgumentException("Secret key must be at least 32 bytes");

        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
    }

    /**
     * Access Token 생성
     *
     * @param userInfoDTO User Information
     * @return Access Token String
     */
    public AuthResponse createAccessToken(UserInfoDTO userInfoDTO) {

        return createToken(userInfoDTO, accessTokenExpTime);
    }

    /**
     * JWT 생성
     *
     * @param userInfoDTO User Information
     * @param expireTime  Expire Time
     * @return JWT String
     */
    private AuthResponse createToken(UserInfoDTO userInfoDTO, long expireTime) {
        Claims claims = Jwts.claims().setSubject(userInfoDTO.getUsername());

        LocalDateTime expiresAt = LocalDateTime.now().plusHours(expireTime);
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(expiresAt.atZone(ZoneId.of("Asia/Seoul")).toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String exp = expiresAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

        return new AuthResponse(token, userInfoDTO.getUsername(), exp);
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
            throw new InvalidJwtTokenException("Invalid JWT", e);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtTokenException("Expired JWT", e);
        } catch (UnsupportedJwtException e) {
            throw new InvalidJwtTokenException("Unsupported JWT", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidJwtTokenException("Illegal JWT", e);
        }
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
     * @param token JWT Token
     * @return Username
     */
    public String getUsername(String token) {
        return parseClaims(token).get("username", String.class);
    }

    public Date getExpiresAt(String token) {
        return parseClaims(token).getExpiration();
    }
}