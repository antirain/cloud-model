package com.cloud.product.utils;

import cn.hutool.core.util.IdUtil;

/**
 * @author local
 * @date 2025-09-29
 * @description
 */
public class ProductIdGenerator {
    
    // 商品ID前缀
    private static final String PRODUCT_PREFIX = "P";
    
    // SKU ID前缀
    private static final String SKU_PREFIX = "S";
    
    // 分类ID前缀
    private static final String CATEGORY_PREFIX = "C";
    
    /**
     * 生成商品ID
     * @return 商品ID
     */
    public static String generateProductId() {
        return PRODUCT_PREFIX + IdUtil.fastUUID().replace("-", "").substring(0, 20);
    }
    
    /**
     * 生成SKU ID
     * @return SKU ID
     */
    public static String generateSkuId() {
        return SKU_PREFIX + IdUtil.fastUUID().replace("-", "").substring(0, 20);
    }
    
    /**
     * 生成分类ID
     * @return 分类ID
     */
    public static String generateCategoryId() {
        return CATEGORY_PREFIX + IdUtil.fastUUID().replace("-", "").substring(0, 10);
    }
    
    /**
     * 使用完整UUID生成商品ID
     * @return 完整UUID格式的商品ID
     */
    public static String generateFullUuidProductId() {
        return PRODUCT_PREFIX + IdUtil.fastUUID().replace("-", "");
    }
    
    /**
     * 使用完整UUID生成SKU ID
     * @return 完整UUID格式的SKU ID
     */
    public static String generateFullUuidSkuId() {
        return SKU_PREFIX + IdUtil.fastUUID().replace("-", "");
    }
}