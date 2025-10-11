package com.cloud.order.service;

import com.cloud.order.entity.CartItem;

import java.util.List;
import java.util.Map;

/**
  * @author local
  * @date 2025-09-29
  * @description 购物车服务接口
  */
public interface CartService {

    /**
     * 获取当前登录用户购物车中的所有商品
     * @return 购物车商品列表
     */
    List<CartItem> getUserCartItems();

    /**
     * 将商品添加到当前登录用户的购物车
     * @param item 购物车商品
     * @return 添加结果
     */
    boolean addToCart(CartItem item);

    /**
     * 从当前登录用户的购物车中移除商品
     * @param skuId 商品SKU ID
     * @return 移除结果
     */
    boolean removeFromCart(String skuId);

    /**
     * 更新当前登录用户购物车中商品的数量
     * @param skuId 商品SKU ID
     * @param quantity 新的数量
     * @return 更新结果
     */
    boolean updateCartItemQuantity(String skuId, Integer quantity);

    /**
     * 选中/取消选中当前登录用户购物车中的商品
     * @param skuIds 商品SKU ID列表
     * @param selected 是否选中
     * @return 更新结果
     */
    boolean updateCartItemSelection(List<String> skuIds, boolean selected);

    /**
     * 清空当前登录用户的购物车
     * @return 清空结果
     */
    boolean clearCart();

    /**
     * 获取当前登录用户购物车中选中的商品
     * @return 选中的商品列表
     */
    List<CartItem> getSelectedCartItems();

    /**
     * 计算当前登录用户购物车中选中商品的总金额
     * @return 总金额信息，包含总金额、总数量、商品数和选中的商品列表
     */
    Map<String, Object> calculateSelectedTotal();
}