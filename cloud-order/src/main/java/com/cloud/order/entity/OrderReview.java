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
 * 订单评价表
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Getter
@Setter
@TableName("order_review")
@Schema(name = "OrderReview", description = "订单评价表")
public class OrderReview extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID")
    @TableField("order_id")
    private Long orderId;

    @Schema(description = "订单项ID")
    @TableField("item_id")
    private Long itemId;

    @Schema(description = "商品ID")
    @TableField("product_id")
    private Long productId;

    @Schema(description = "商品SKU ID")
    @TableField("sku_id")
    private Long skuId;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "评分(1-5星)")
    @TableField("rating")
    private Integer rating;

    @Schema(description = "评价内容")
    @TableField("content")
    private String content;

    @Schema(description = "评价图片URL，多个图片用逗号分隔")
    @TableField("image_urls")
    private String imageUrls;

    @Schema(description = "评价视频URL")
    @TableField("video_url")
    private String videoUrl;

    @Schema(description = "评价类型: 1(文字), 2(图片), 3(视频)")
    @TableField("review_type")
    private Integer reviewType;

    @Schema(description = "是否匿名评价: 0-否, 1-是")
    @TableField("is_anonymous")
    private Boolean isAnonymous;

    @Schema(description = "评价状态: 1(待审核), 2(已通过), 3(已拒绝)")
    @TableField("status")
    private Integer status;

    @Schema(description = "点赞数")
    @TableField("likes_count")
    private Integer likesCount;

    @Schema(description = "商家回复内容")
    @TableField("reply_content")
    private String replyContent;

    @Schema(description = "商家回复时间")
    @TableField("reply_time")
    private LocalDateTime replyTime;
}
