package com.cloud.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration:86400}") // 默认 24 小时
    private long EXPIRATION;

    private SecretKey key;

    @PostConstruct
    public void init() {
        // ✅ 兼容所有版本：从 SECRET_KEY 生成 SecretKey
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String subject) {
        return generateToken(subject, new HashMap<>());
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .signWith(key)
                .compact();
    }

    // ✅ 提取 Claim（兼容写法）
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ✅ 解析所有 Claims（兼容 0.12.5 ~ 0.15.0）
    private Claims extractAllClaims(String token) {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }

    // ✅ 校验 Token
    public boolean validateToken(String token, String subject) {
        try {
            final String username = extractSubject(token);
            return (username.equals(subject) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}