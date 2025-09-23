package com.cloud.common.enums;
/**
 * @author local
 * @date 2025-09-22
 * @description 数据脱敏类型
 */
public enum SensitiveType {
    /**
     * 手机号：保留前3后4，中间****
     */
    PHONE,

    /**
     * 身份证：保留前2后4，中间****
     */
    ID_CARD,

    /**
     * 银行卡：保留前4后4，中间****
     */
    BANK_CARD,

    /**
     * 自定义：需配合 maskPattern 使用
     */
    CUSTOM
}