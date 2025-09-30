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
 * 订单状态历史表
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Getter
@Setter
@TableName("order_status_history")
@Schema(name = "OrderStatusHistory", description = "订单状态历史表")
public class OrderStatusHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID")
    @TableField("order_id")
    private Long orderId;

    @Schema(description = "变更前状态")
    @TableField("old_status")
    private Integer oldStatus;

    @Schema(description = "变更后状态")
    @TableField("new_status")
    private Integer newStatus;

    @Schema(description = "状态变更时间")
    @TableField("status_change_time")
    private LocalDateTime statusChangeTime;

    @Schema(description = "操作人ID")
    @TableField("operator_id")
    private Long operatorId;

    @Schema(description = "操作人类型: 1(系统), 2(用户), 3(管理员)")
    @TableField("operator_type")
    private Integer operatorType;

    @Schema(description = "备注信息")
    @TableField("remark")
    private String remark;
}
