package com.cloud.product.entity;

import com.cloud.common.datasource.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author local
 * @date 2025-09-29
 * @description 商品SKU实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductSku extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * SKU属性（JSON格式，如{"颜色":"红色","尺码":"M"}）
     */
    private Map<String, String> attributes;

    /**
     * 销售价格
     */
    private BigDecimal price;

    /**
     * 成本价格
     */
    private BigDecimal costPrice;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 销量
     */
    private Integer salesVolume;

    /**
     * SKU图片
     */
    private String skuImage;

    /**
     * SKU状态：1(在售)、2(下架)、3(删除)
     */
    private Integer status;

}