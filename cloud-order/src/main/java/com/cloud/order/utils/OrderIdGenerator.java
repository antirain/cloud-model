package com.cloud.order.utils;

import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 订单ID生成器
 * 提供多种订单ID生成策略，适应不同业务场景
 */
@Component
public class OrderIdGenerator {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final Random RANDOM = new Random();
    private static final String ORDER_PREFIX = "ORDER-";
    
    /**
     * 使用UUID生成订单ID（当前项目中使用的方式）
     * 优点：实现简单，全局唯一
     * 缺点：可读性较差，无业务含义
     * @return 订单ID，格式为 ORDER-xxxxxxx（8位UUID）
     */
    public String generateSimpleOrderId() {
        return ORDER_PREFIX + IdUtil.fastUUID().substring(0, 8);
    }
    
    /**
     * 使用完整UUID生成订单ID
     * 优点：全局唯一，冲突概率极低
     * 缺点：ID较长，可读性较差
     * @return 完整UUID格式的订单ID
     */
    public String generateFullUuidOrderId() {
        return ORDER_PREFIX + IdUtil.fastUUID().replace("-", "");
    }
    
    /**
     * 使用时间戳+随机数生成订单ID
     * 优点：具有时序性，便于排序和追踪
     * 缺点：高并发场景下有一定概率冲突
     * @return 时间戳+随机数格式的订单ID
     */
    public String generateTimestampOrderId() {
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String random = String.format("%06d", RANDOM.nextInt(1000000));
        return ORDER_PREFIX + timestamp + random;
    }
    
    /**
     * 使用雪花算法生成订单ID（推荐用于生产环境）
     * 优点：全局唯一，具有时序性，性能高
     * 缺点：需要配置机器ID，实现相对复杂
     * @return 雪花算法生成的订单ID
     */
    public String generateSnowflakeOrderId() {
        // 注意：在实际生产环境中，应该使用配置好的雪花算法生成器
        // 这里使用Hutool提供的简单实现，实际使用时应该根据项目需求配置workerId和datacenterId
        long snowflakeId = IdUtil.getSnowflakeNextId();
        return ORDER_PREFIX + snowflakeId;
    }
    
    /**
     * 生成包含业务标识的订单ID
     * 优点：包含业务信息，便于识别和分类
     * 缺点：ID较长
     * @param businessType 业务类型标识（如：普通订单-ORD，秒杀订单-FLASH）
     * @return 包含业务标识的订单ID
     */
    public String generateBusinessOrderId(String businessType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        String random = String.format("%04d", RANDOM.nextInt(10000));
        return ORDER_PREFIX + businessType + "-" + timestamp + random;
    }
    
    /**
     * 生成包含用户标识的订单ID
     * 优点：可以快速识别订单属于哪个用户
     * 缺点：泄露用户ID信息
     * @param userId 用户ID
     * @return 包含用户标识的订单ID
     */
    public String generateUserOrderId(String userId) {
        // 为了安全，这里对用户ID进行简单处理，实际使用时可以考虑更复杂的加密或混淆方案
        String shortUserId = userId.length() > 6 ? userId.substring(userId.length() - 6) : userId;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmm"));
        return ORDER_PREFIX + shortUserId + "-" + timestamp;
    }
}