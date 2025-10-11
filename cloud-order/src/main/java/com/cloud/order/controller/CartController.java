package com.cloud.order.controller;

import com.cloud.order.entity.CartItem;
import com.cloud.order.service.CartService;
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
 * @description 购物车控制器
 */
@RestController
@RequestMapping("/cart")
@Tag(name = "购物车服务", description = "购物车相关接口")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 获取用户购物车中的所有商品
     */
    @GetMapping("/items")
    @Operation(summary = "获取购物车商品列表", description = "获取当前登录用户购物车中的所有商品")
    public Result<List<CartItem>> getUserCartItems() {
        List<CartItem> items = cartService.getUserCartItems();
        return Result.success(items);
    }

    /**
     * 将商品添加到购物车
     */
    @PostMapping("/add")
    @Operation(summary = "添加商品到购物车", description = "将商品添加到当前登录用户的购物车")
    public Result<Boolean> addToCart(@RequestBody CartItem item) {
        boolean result = cartService.addToCart(item);
        return Result.success(result);
    }

    /**
     * 从购物车中移除商品
     */
    @DeleteMapping("/remove")
    @Operation(summary = "从购物车移除商品", description = "从当前登录用户的购物车中移除指定的商品")
    public Result<Boolean> removeFromCart(@RequestParam("skuId") String skuId) {
        boolean result = cartService.removeFromCart(skuId);
        return Result.success(result);
    }

    /**
     * 更新购物车中商品的数量
     */
    @PutMapping("/update-quantity")
    @Operation(summary = "更新购物车商品数量", description = "更新购物车中指定商品的数量")
    public Result<Boolean> updateCartItemQuantity(@RequestParam("skuId") String skuId, 
                                                 @RequestParam("quantity") Integer quantity) {
        boolean result = cartService.updateCartItemQuantity(skuId, quantity);
        return Result.success(result);
    }

    /**
     * 选中/取消选中购物车中的商品
     */
    @PutMapping("/update-selection")
    @Operation(summary = "更新购物车商品选中状态", description = "选中或取消选中购物车中的商品")
    public Result<Boolean> updateCartItemSelection(@RequestBody List<String> skuIds, 
                                                  @RequestParam("selected") boolean selected) {
        boolean result = cartService.updateCartItemSelection(skuIds, selected);
        return Result.success(result);
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clear")
    @Operation(summary = "清空购物车", description = "清空当前登录用户的购物车")
    public Result<Boolean> clearCart() {
        boolean result = cartService.clearCart();
        return Result.success(result);
    }

    /**
     * 获取购物车中选中的商品
     */
    @GetMapping("/selected-items")
    @Operation(summary = "获取购物车选中商品", description = "获取购物车中选中的商品")
    public Result<List<CartItem>> getSelectedCartItems() {
        List<CartItem> items = cartService.getSelectedCartItems();
        return Result.success(items);
    }

    /**
     * 计算购物车中选中商品的总金额
     */
    @GetMapping("/calculate-total")
    @Operation(summary = "计算购物车选中商品总金额", description = "计算购物车中选中商品的总金额和数量")
    public Result<Map<String, Object>> calculateSelectedTotal() {
        Map<String, Object> result = cartService.calculateSelectedTotal();
        return Result.success(result);
    }
}