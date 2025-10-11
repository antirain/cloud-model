package com.cloud.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.common.datasource.entity.BaseEntity;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单物流信息表
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Getter
@Setter
@TableName("order_logistics")
@Schema(name = "OrderLogistics", description = "订单物流信息表")
public class OrderLogistics extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID")
    @TableField("order_id")
    private Long orderId;

    @Schema(description = "物流公司名称")
    @TableField("logistics_company")
    private String logisticsCompany;

    @Schema(description = "物流单号")
    @TableField("tracking_number")
    private String trackingNumber;

    @Schema(description = "物流状态: 1(未发货), 2(运输中), 3(已送达), 4(已签收)")
    @TableField("logistics_status")
    private Integer logisticsStatus;

    @Schema(description = "发件人姓名")
    @TableField("sender_name")
    private String senderName;

    @Schema(description = "发件人电话")
    @TableField("sender_phone")
    private String senderPhone;

    @Schema(description = "发件人详细地址")
    @TableField("sender_address")
    private String senderAddress;

    @Schema(description = "收件人姓名")
    @TableField("receiver_name")
    private String receiverName;

    @Schema(description = "收件人电话")
    @TableField("receiver_phone")
    private String receiverPhone;

    @Schema(description = "收件人详细地址")
    @TableField("receiver_address")
    private String receiverAddress;

    @Schema(description = "发货时间")
    @TableField("shipping_time")
    private LocalDateTime shippingTime;

    @Schema(description = "送达时间")
    @TableField("delivery_time")
    private LocalDateTime deliveryTime;

    @Schema(description = "签收时间")
    @TableField("sign_time")
    private LocalDateTime signTime;

    @Schema(description = "最新物流信息")
    @TableField("last_logistics_info")
    private String lastLogisticsInfo;
}
