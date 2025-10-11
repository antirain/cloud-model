//package com.cloud.common.redis.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
///**
// * @author local
// * @date 2025/9/26 11:49
// * @description
// */
//@Service
//public class TokenBlacklistService {
//
//    private final StringRedisTemplate redisTemplate;
//
//    // 黑名单前缀
//    private static final String BLACKLIST_PREFIX = "blacklist:jti:";
//    // 全局登出时间戳前缀
//    private static final String GLOBAL_LOGOUT_PREFIX = "user:global_logout:";
//
//    @Value("${jwt.expiration-ms}")
//    private long TOKEN_EXPIRATION_MS;
//
//    public TokenBlacklistService(StringRedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    // 1. 使单个 Token 失效（退出当前设备）
//    public void blacklistToken(String jti) {
//        long ttlSeconds = TimeUnit.MILLISECONDS.toSeconds(TOKEN_EXPIRATION_MS);
//        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + jti, "1", ttlSeconds, TimeUnit.SECONDS);
//    }
//
//    // 2. 使用户所有 Token 失效（退出所有设备）
//    public void logoutAllDevices(String userId) {
//        // 记录登出时间戳（精确到毫秒）
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        // TTL 设为最大可能 Token 生命周期（如 7 天）
//        redisTemplate.opsForValue().set(
//                GLOBAL_LOGOUT_PREFIX + userId,
//                timestamp,
//                7, TimeUnit.DAYS
//        );
//    }
//
//    // 3. 检查 Token 是否被单个拉黑
//    public boolean isTokenBlacklisted(String jti) {
//        return redisTemplate.hasKey(BLACKLIST_PREFIX + jti);
//    }
//
//    // 4. 检查用户是否全局登出（且 Token 签发时间早于登出时间）
//    public boolean isUserGloballyLoggedOut(String userId, long tokenIssuedAt) {
//        String timestampStr = redisTemplate.opsForValue().get(GLOBAL_LOGOUT_PREFIX + userId);
//        if (timestampStr == null) {
//            return false;
//        }
//        long globalLogoutTime = Long.parseLong(timestampStr);
//        return tokenIssuedAt < globalLogoutTime;
//    }
//}