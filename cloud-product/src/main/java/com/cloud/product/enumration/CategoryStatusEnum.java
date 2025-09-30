package com.cloud.product.enumration;

import lombok.Getter;

/**
 * 分类状态枚举类
 * 用于表示分类的状态信息
 */
@Getter
public enum CategoryStatusEnum {
    
    /**
     * 启用状态
     */
    ENABLED(1, "启用"),
    
    /**
     * 禁用状态
     */
    DISABLED(0, "禁用");
    
    private final Integer code;
    private final String desc;
    
    /**
     * 构造方法
     * @param code 状态码
     * @param desc 状态描述
     */
    CategoryStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    /**
     * 根据状态码获取枚举对象
     * @param code 状态码
     * @return 枚举对象
     */
    public static CategoryStatusEnum getByCode(Integer code) {
        for (CategoryStatusEnum status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}