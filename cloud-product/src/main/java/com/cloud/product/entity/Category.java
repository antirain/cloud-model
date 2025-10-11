package com.cloud.product.entity;

import com.cloud.common.datasource.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author local
 * @date 2025-09-29
 * @description 商品分类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Category  extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 父分类ID（顶级分类为0或null）
     */
    private Long parentId;

    /**
     * 分类层级
     */
    private Integer level;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 分类状态：1(启用)、0(禁用)
     */
    private Integer status;

}