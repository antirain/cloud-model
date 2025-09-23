package com.cloud.common.security.core;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * ✅ 纯静态工具类，兼容 JDK 17/21 + JJWT 0.12.5+
 * 无任何 Spring / Reactor 依赖
 */
public class JwtUtils {

    // 👇 推荐从配置中心或环境变量读取，这里写死仅作演示
    private static final String SECRET_KEY = "your-256-bit-secret-key-must-be-at-least-32-characters-long!";
    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000L;

    private static SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public static String getSubjectFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}