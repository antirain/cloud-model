package com.cloud.order.enumration;

import lombok.Getter;

/**
 * 操作人类型枚举类
 * 用于管理操作人的类型
 */
@Getter
public enum OperatorTypeEnum {
    
    /**
     * 系统
     */
    SYSTEM(1, "系统"),
    
    /**
     * 用户
     */
    USER(2, "用户"),
    
    /**
     * 管理员
     */
    ADMIN(3, "管理员"),
    
    /**
     * 客服
     */
    CUSTOMER_SERVICE(4, "客服");
    
    private final Integer code;
    private final String desc;
    
    OperatorTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    /**
     * 根据code获取枚举值
     * @param code 类型码
     * @return 枚举值
     */
    public static OperatorTypeEnum getByCode(Integer code) {
        for (OperatorTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}