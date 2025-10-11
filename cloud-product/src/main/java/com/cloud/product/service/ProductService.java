package com.cloud.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.product.entity.Product;
import com.cloud.product.entity.ProductSku;

import java.util.List;
import java.util.Map;

/**
 * @author local
 * @date 2025-09-29
 * @description
 */
public interface ProductService extends IService<Product> {

    /**
     * 根据商品ID获取商品详情
     * @param productId 商品ID
     * @return 商品详情
     */
    Product getProductById(Long productId);

    /**
     * 根据商品ID列表获取商品列表
     * @param productIds 商品ID列表
     * @return 商品列表
     */
    List<Product> getProductsByIds(List<Long> productIds);

    /**
     * 分页查询商品列表
     * @param params 查询参数
     * @param page 页码
     * @param size 每页大小
     * @return 商品列表
     */
    List<Product> getProductList(Map<String, Object> params, Integer page, Integer size);

    /**
     * 根据SKU ID获取SKU详情
     * @param skuId SKU ID
     * @return SKU详情
     */
    ProductSku getSkuById(Long skuId);

    /**
     * 根据商品ID获取SKU列表
     * @param productId 商品ID
     * @return SKU列表
     */
    List<ProductSku> getSkusByProductId(Long productId);

    /**
     * 根据SKU ID列表获取SKU列表
     * @param skuIds SKU ID列表
     * @return SKU列表
     */
    List<ProductSku> getSkusByIds(List<Long> skuIds);

    /**
     * 扣减商品库存
     * @param skuId SKU ID
     * @param quantity 扣减数量
     * @return 扣减结果
     */
    boolean deductStock(Long skuId, Integer quantity);

    /**
     * 增加商品库存
     * @param skuId SKU ID
     * @param quantity 增加数量
     * @return 增加结果
     */
    boolean addStock(Long skuId, Integer quantity);
}