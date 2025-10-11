package com.cloud.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 订单支付记录表
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Getter
@Setter
@TableName("order_payment")
@Schema(name = "OrderPayment", description = "订单支付记录表")
public class OrderPayment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID")
    @TableField("order_id")
    private Long orderId;

    @Schema(description = "第三方支付交易ID")
    @TableField("transaction_id")
    private Long transactionId;

    @Schema(description = "支付金额")
    @TableField("amount")
    private BigDecimal amount;

    @Schema(description = "货币类型")
    @TableField("currency")
    private String currency;

    @Schema(description = "支付方式: 1(支付宝), 2(微信支付), 3(信用卡)")
    @TableField("payment_method")
    private Integer paymentMethod;

    @Schema(description = "支付状态: 1(初始), 2(处理中), 3(成功), 4(失败), 5(已退款), 6(已关闭)")
    @TableField("payment_status")
    private Integer paymentStatus;

    @Schema(description = "支付完成时间")
    @TableField("payment_time")
    private LocalDateTime paymentTime;

    @Schema(description = "支付IP地址")
    @TableField("ip_address")
    private String ipAddress;

    @Schema(description = "支付渠道返回数据(序列化存储)")
    @TableField("payment_channel_data")
    private String paymentChannelData;
}
