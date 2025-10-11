package com.cloud.common.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author local
 */
public class JwtOperator {

    private final SecretKey key;
    private final Long accessExpiration;
    private final Long refreshExpiration;
    private final String header;
    private final String prefix;

    public JwtOperator(
            String secret,
            Long accessExpiration,
            Long refreshExpiration,
            String header,
            String prefix) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.header = header;
        this.prefix = prefix.trim() + " ";
    }

    public long getAccessExpiration() {
        return accessExpiration;
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        // JWT标准使用Date类型，这里使用LocalDateTime并转换为Date
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusSeconds(accessExpiration);
        
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(Date.from(now.toInstant(ZoneOffset.UTC)))
                .expiration(Date.from(expiration.toInstant(ZoneOffset.UTC)))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String subject) {
        // JWT标准使用Date类型，这里使用LocalDateTime并转换为Date
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusDays(refreshExpiration);
        
        return Jwts.builder()
                .subject(subject)
                .issuedAt(Date.from(now.toInstant(ZoneOffset.UTC)))
                .expiration(Date.from(expiration.toInstant(ZoneOffset.UTC)))
                .signWith(key)
                .compact();
    }

    public Claims validate(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String validateRefreshToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = validate(token);
        return claimsResolver.apply(claims);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
    }

    public Boolean validateToken(String token, String username) {
        try {
            final String tokenUsername = getClaimFromToken(token, Claims::getSubject);
            return (tokenUsername.equals(username) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}