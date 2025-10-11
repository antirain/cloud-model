package com.cloud.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.product.entity.Product;
import com.cloud.product.entity.ProductSku;
import com.cloud.product.enumration.ProductStatusEnum;
import com.cloud.product.mapper.ProductMapper;
import com.cloud.product.mapper.ProductSkuMapper;
import com.cloud.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author local
 * @date 2025-09-29
 * @description
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    private final ProductSkuMapper skuMapper;

    @Override
    public Product getProductById(Long productId) {
        Product product = this.getById(productId);
        // 在实际项目中，这里应该从数据库查询
        return null;
    }

    @Override
    public List<Product> getProductsByIds(List<Long> productIds) {
        // 在实际项目中，这里应该从数据库批量查询
        return null;
    }

    @Override
    public List<Product> getProductList(Map<String, Object> params, Integer page, Integer size) {
        // 在实际项目中，这里应该根据参数进行分页查询
        // 这里仅返回所有在售商品的列表
        return null;
    }

    @Override
    public ProductSku getSkuById(Long skuId) {
        // 在实际项目中，这里应该从数据库查询
        return null;
    }

    @Override
    public List<ProductSku> getSkusByProductId(Long productId) {
        // 在实际项目中，这里应该从数据库查询
        return skuMapper.selectList(new LambdaQueryWrapper<ProductSku>()
                .eq(ProductSku::getProductId, productId));
    }

    @Override
    public List<ProductSku> getSkusByIds(List<Long> skuIds) {
        return skuMapper.selectByIds(skuIds);
    }

    @Override
    @Transactional
    public boolean deductStock(Long skuId, Integer quantity) {
        // 在实际项目中，这里应该使用事务确保库存操作的原子性
        Assert.notNull(skuId, "商品SKU ID不能为空");
        ProductSku sku = skuMapper.selectById(skuId);
        if (sku == null || !ProductStatusEnum.ON_SALE.getCode().equals(sku.getStatus()) || sku.getStock() < quantity) {
            return false;
        }
        sku.setStock(sku.getStock() - quantity);
        sku.setSalesVolume(sku.getSalesVolume() + quantity);
        sku.setUpdateTime(LocalDateTime.now());
        skuMapper.updateById(sku);
        return true;
    }

    @Override
    @Transactional
    public boolean addStock(Long skuId, Integer quantity) {
        // 在实际项目中，这里应该使用事务确保库存操作的原子性
        Assert.notNull(skuId, "商品SKU ID不能为空");
        ProductSku sku = skuMapper.selectById(skuId);
        if (sku == null) {
            return false;
        }
        sku.setStock(sku.getStock() + quantity);
        sku.setUpdateTime(LocalDateTime.now());
        skuMapper.updateById(sku);
        return true;
    }
}