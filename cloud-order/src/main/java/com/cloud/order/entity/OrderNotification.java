package com.cloud.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.common.datasource.entity.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单消息通知表
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Getter
@Setter
@TableName("order_notification")
@Schema(name = "OrderNotification", description = "订单消息通知表")
public class OrderNotification extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID")
    @TableField("order_id")
    private Long orderId;

    @Schema(description = "接收用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "通知类型: 1(订单状态变更), 2(支付成功), 3(发货通知), 4(退款状态变更)")
    @TableField("notification_type")
    private Integer notificationType;

    @Schema(description = "通知标题")
    @TableField("title")
    private String title;

    @Schema(description = "通知内容")
    @TableField("content")
    private String content;

    @Schema(description = "是否已读: 0-未读, 1-已读")
    @TableField("is_read")
    private Boolean isRead;

    @Schema(description = "阅读时间")
    @TableField("read_time")
    private LocalDateTime readTime;
}
