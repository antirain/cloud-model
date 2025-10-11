package com.cloud.order.enumration;

import lombok.Getter;

/**
 * 退款状态枚举类
 * 用于管理退款流程中的各种状态
 */
@Getter
public enum RefundStatusEnum {
    
    /**
     * 待处理
     */
    PENDING(1, "待处理"),
    
    /**
     * 处理中
     */
    PROCESSING(2, "处理中"),
    
    /**
     * 已同意
     */
    APPROVED(3, "已同意"),
    
    /**
     * 已拒绝
     */
    REJECTED(4, "已拒绝"),
    
    /**
     * 退款中
     */
    REFUNDING(5, "退款中"),
    
    /**
     * 已完成
     */
    SUCCESS(6, "已完成"),
    
    /**
     * 已关闭
     */
    CLOSED(7, "已关闭");
    
    private final Integer code;
    private final String desc;
    
    RefundStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    /**
     * 根据code获取枚举值
     * @param code 状态码
     * @return 枚举值
     */
    public static RefundStatusEnum getByCode(Integer code) {
        for (RefundStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}