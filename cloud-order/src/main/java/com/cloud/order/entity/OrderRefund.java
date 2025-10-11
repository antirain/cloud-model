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
 * 订单退款申请表
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Getter
@Setter
@TableName("order_refund")
@Schema(name = "OrderRefund", description = "订单退款申请表")
public class OrderRefund extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID")
    @TableField("order_id")
    private Long orderId;

    @Schema(description = "订单项ID")
    @TableField("item_id")
    private Long itemId;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "退款金额")
    @TableField("refund_amount")
    private BigDecimal refundAmount;

    @Schema(description = "货币类型")
    @TableField("currency")
    private String currency;

    @Schema(description = "退款原因")
    @TableField("refund_reason")
    private String refundReason;

    @Schema(description = "退款类型: 1(仅退款), 2(退货退款)")
    @TableField("refund_type")
    private Integer refundType;

    @Schema(description = "退款状态: 1(待处理), 2(处理中), 3(已同意), 4(已拒绝), 5(退款中), 6(已完成), 7(已关闭)")
    @TableField("refund_status")
    private Integer refundStatus;

    @Schema(description = "申请时间")
    @TableField("application_time")
    private LocalDateTime applicationTime;

    @Schema(description = "处理时间")
    @TableField("process_time")
    private LocalDateTime processTime;

    @Schema(description = "退款完成时间")
    @TableField("refund_finish_time")
    private LocalDateTime refundFinishTime;

    @Schema(description = "拒绝原因")
    @TableField("reject_reason")
    private String rejectReason;

    @Schema(description = "操作人ID")
    @TableField("operator_id")
    private Long operatorId;

    @Schema(description = "操作人类型: 1(系统), 2(用户), 3(管理员), 4(客服)")
    @TableField("operator_type")
    private Integer operatorType;

    @Schema(description = "凭证图片URL，多个图片用逗号分隔")
    @TableField("proof_images")
    private String proofImages;

    @Schema(description = "退款交易ID")
    @TableField("transaction_id")
    private Long transactionId;
}
