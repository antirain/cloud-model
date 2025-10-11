package com.cloud.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.common.datasource.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单商品详情表
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Getter
@Setter
@TableName("order_item")
@Schema(name = "OrderItem", description = "订单商品详情表")
public class OrderItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID")
    @TableField("order_id")
    private Long orderId;

    @Schema(description = "商品ID")
    @TableField("product_id")
    private Long productId;

    @Schema(description = "商品SKU ID")
    @TableField("sku_id")
    private Long skuId;

    @Schema(description = "商品名称")
    @TableField("product_name")
    private String productName;

    @Schema(description = "SKU名称")
    @TableField("sku_name")
    private String skuName;

    @Schema(description = "商品图片")
    @TableField("product_image")
    private String productImage;

    @Schema(description = "购买数量")
    @TableField("quantity")
    private Integer quantity;

    @Schema(description = "商品单价")
    @TableField("price")
    private BigDecimal price;

    @Schema(description = "商品总价")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @Schema(description = "订单项状态: 1(正常), 2(异常), 3(退款中), 4(已退款)")
    @TableField("item_status")
    private Integer itemStatus;
}
