package com.cloud.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.common.datasource.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单主表
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Getter
@Setter
@TableName("order_main")
@Schema(name = "OrderMain", description = "订单主表")
public class OrderMain extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "订单总金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @Schema(description = "实际支付金额")
    @TableField("actual_pay_amount")
    private BigDecimal actualPayAmount;

    @Schema(description = "运费")
    @TableField("shipping_fee")
    private BigDecimal shippingFee;

    @Schema(description = "优惠券ID")
    @TableField("coupon_id")
    private Long couponId;

    @Schema(description = "优惠金额")
    @TableField("discount_amount")
    private BigDecimal discountAmount;

    @Schema(description = "订单状态: 1(待支付), 2(待发货), 3(已发货), 4(已完成), 5(已取消), 6(已关闭)")
    @TableField("order_status")
    private Integer orderStatus;

    @Schema(description = "支付状态: 1(未支付), 2(已支付), 3(已退款), 4(部分退款)")
    @TableField("payment_status")
    private Integer paymentStatus;

    @Schema(description = "支付方式: 1(支付宝), 2(微信支付), 3(信用卡)")
    @TableField("payment_type")
    private Integer paymentType;

    @Schema(description = "支付时间")
    @TableField("payment_time")
    private LocalDateTime paymentTime;

    @Schema(description = "收货地址ID")
    @TableField("shipping_address_id")
    private Long shippingAddressId;

    @Schema(description = "收货人姓名")
    @TableField("consignee_name")
    private String consigneeName;

    @Schema(description = "收货人电话")
    @TableField("consignee_phone")
    private String consigneePhone;

    @Schema(description = "收货人详细地址")
    @TableField("consignee_address")
    private String consigneeAddress;

    @Schema(description = "订单备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "删除标记: 0-未删除, 1-已删除")
    @TableField("deleted")
    @TableLogic
    private Boolean deleted;
}
