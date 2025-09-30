package com.cloud.product.enumration;

import lombok.Getter;

/**
 * 商品状态枚举类
 * 用于表示商品的状态信息
 */
@Getter
public enum ProductStatusEnum {
    
    /**
     * 在售状态
     */
    ON_SALE(1, "在售"),
    
    /**
     * 下架状态
     */
    OFF_SALE(2, "下架"),
    
    /**
     * 删除状态
     */
    DELETED(3, "删除");
    
    private final Integer code;
    private final String desc;
    
    /**
     * 构造方法
     * @param code 状态码
     * @param desc 状态描述
     */
    ProductStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    /**
     * 根据状态码获取枚举对象
     * @param code 状态码
     * @return 枚举对象
     */
    public static ProductStatusEnum getByCode(Integer code) {
        for (ProductStatusEnum status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}