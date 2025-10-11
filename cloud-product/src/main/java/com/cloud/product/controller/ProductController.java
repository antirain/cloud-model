package com.cloud.product.controller;

import com.cloud.product.entity.Product;
import com.cloud.product.entity.ProductSku;
import com.cloud.product.service.ProductService;
import com.cloud.common.web.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author local
 * @date 2025-09-29
 * @description 商品控制器
 */
@RestController
@RequestMapping("/product")
@Tag(name = "商品服务", description = "商品相关接口")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 获取商品详情
     */
    @GetMapping("/detail/{productId}")
    @Operation(summary = "获取商品详情", description = "根据商品ID获取商品详情")
    public Result<Product> getProductById(@PathVariable("productId") Long productId) {
        Product product = productService.getProductById(productId);
        return Result.success(product);
    }

    /**
     * 批量获取商品详情
     */
    @PostMapping("/batch")
    @Operation(summary = "批量获取商品详情", description = "根据商品ID列表批量获取商品详情")
    public Result<List<Product>> getProductsByIds(@RequestBody List<Long> productIds) {
        List<Product> products = productService.getProductsByIds(productIds);
        return Result.success(products);
    }

    /**
     * 获取商品列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取商品列表", description = "分页获取商品列表")
    public Result<List<Product>> getProductList(
            @RequestParam Map<String, Object> params,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<Product> products = productService.getProductList(params, page, size);
        return Result.success(products);
    }

    /**
     * 获取SKU详情
     */
    @GetMapping("/sku/detail/{skuId}")
    @Operation(summary = "获取SKU详情", description = "根据SKU ID获取SKU详情")
    public Result<ProductSku> getSkuById(@PathVariable("skuId") Long skuId) {
        ProductSku sku = productService.getSkuById(skuId);
        return Result.success(sku);
    }

    /**
     * 获取商品的所有SKU
     */
    @GetMapping("/skus/{productId}")
    @Operation(summary = "获取商品SKU列表", description = "根据商品ID获取该商品的所有SKU")
    public Result<List<ProductSku>> getSkusByProductId(@PathVariable("productId") Long productId) {
        List<ProductSku> skus = productService.getSkusByProductId(productId);
        return Result.success(skus);
    }

    /**
     * 批量获取SKU详情
     */
    @PostMapping("/sku/batch")
    @Operation(summary = "批量获取SKU详情", description = "根据SKU ID列表批量获取SKU详情")
    public Result<List<ProductSku>> getSkusByIds(@RequestBody List<Long> skuIds) {
        List<ProductSku> skus = productService.getSkusByIds(skuIds);
        return Result.success(skus);
    }
}