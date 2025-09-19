package com.cloud.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * 令牌验证工具类
 * 提供更全面的令牌验证和解析功能
 */
@Component
public class TokenValidator {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 验证令牌是否有效
     * @param token JWT令牌
     * @return 如果令牌有效则返回true
     */
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        return jwtUtil.validateToken(token);
    }

    /**
     * 验证令牌是否有效且与用户名匹配
     * @param token JWT令牌
     * @param username 用户名
     * @return 如果令牌有效且与用户名匹配则返回true
     */
    public boolean validateTokenForUser(String token, String username) {
        if (!StringUtils.hasText(token) || !StringUtils.hasText(username)) {
            return false;
        }
        return jwtUtil.validateToken(token, username);
    }

    /**
     * 从请求头中提取令牌
     * @param authHeader Authorization请求头
     * @return 提取的令牌，如果无效则返回null
     */
    public String extractTokenFromHeader(String authHeader) {
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }

    /**
     * 从令牌中获取特定声明
     * @param token JWT令牌
     * @param claimsResolver 声明解析函数
     * @return 解析后的声明值
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = jwtUtil.getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从令牌中获取用户ID
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        return getClaimFromToken(token, claims -> {
            Object userId = claims.get("userId");
            if (userId instanceof Integer) {
                return ((Integer) userId).longValue();
            } else if (userId instanceof Long) {
                return (Long) userId;
            } else if (userId instanceof String) {
                return Long.parseLong((String) userId);
            }
            return null;
        });
    }

    /**
     * 从令牌中获取用户角色
     * @param token JWT令牌
     * @return 用户角色列表
     */
    @SuppressWarnings("unchecked")
    public String[] getRolesFromToken(String token) {
        return getClaimFromToken(token, claims -> {
            Object roles = claims.get("roles");
            if (roles instanceof String[]) {
                return (String[]) roles;
            } else if (roles instanceof String) {
                return new String[]{(String) roles};
            }
            return new String[0];
        });
    }

    /**
     * 检查令牌是否即将过期（在指定分钟内）
     * @param token JWT令牌
     * @param minutes 分钟数
     * @return 如果令牌将在指定分钟内过期则返回true
     */
    public boolean isTokenAboutToExpire(String token, int minutes) {
        try {
            Date expiration = jwtUtil.getExpirationDateFromToken(token);
            Date now = new Date();
            // 计算令牌剩余有效时间（毫秒）
            long timeLeft = expiration.getTime() - now.getTime();
            // 转换为分钟并检查是否小于指定分钟数
            return timeLeft > 0 && timeLeft < minutes * 60 * 1000;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * 获取令牌中的所有声明
     * @param token JWT令牌
     * @return 声明Map
     */
    public Map<String, Object> getAllClaimsFromToken(String token) {
        return jwtUtil.getClaimsFromToken(token);
    }
}