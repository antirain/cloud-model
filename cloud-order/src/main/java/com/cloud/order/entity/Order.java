package com.cloud.order.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单主实体
 * 用于表示订单的基本信息
 */
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 实际支付金额
     */
    private BigDecimal actualPayAmount;

    /**
     * 运费
     */
    private BigDecimal shippingFee;

    /**
     * 优惠券ID
     */
    private String couponId;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 订单状态: 1(待支付), 2(待发货), 3(已发货), 4(已完成), 5(已取消), 6(已关闭)
     */
    private Integer orderStatus;

    /**
     * 支付状态: 1(未支付), 2(已支付), 3(已退款), 4(部分退款)
     */
    private Integer paymentStatus;

    /**
     * 支付方式: 1(支付宝), 2(微信支付), 3(信用卡)
     */
    private Integer paymentType;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 收货地址ID
     */
    private String shippingAddressId;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人电话
     */
    private String consigneePhone;

    /**
     * 收货人详细地址
     */
    private String consigneeAddress;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记: 0(未删除), 1(已删除)
     */
    private Integer deleted;
}