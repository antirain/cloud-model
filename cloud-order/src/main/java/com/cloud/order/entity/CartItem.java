package com.cloud.order.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车商品实体
 * 用于表示用户购物车中的商品信息
 */
@Data
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品SKU ID
     */
    private Long skuId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 商品单价
     */
    private BigDecimal price;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 商品总价（单价 * 数量）
     */
    private BigDecimal totalAmount;

    /**
     * 商品选中状态
     */
    private Boolean selected = true;

    /**
     * 计算商品总价
     */
    public void calculateTotalAmount() {
        if (this.price != null && this.quantity != null) {
            this.totalAmount = this.price.multiply(new BigDecimal(this.quantity));
        }
    }
}