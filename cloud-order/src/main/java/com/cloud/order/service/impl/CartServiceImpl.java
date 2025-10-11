package com.cloud.order.service.impl;

import com.cloud.common.web.context.UserContext;
import com.cloud.order.entity.CartItem;
import com.cloud.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
  * @author local
  * @date 2025-09-29
  * @description 购物车服务实现类
  */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    // Redis键前缀
    private static final String CART_KEY_PREFIX = "cart:user:";

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<CartItem> getUserCartItems() {
        Long userId = UserContext.getUserId();
        String cartKey = getCartKey(userId);
        List<Object> items = redisTemplate.opsForList().range(cartKey, 0, -1);
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }
        return items.stream().map(item -> (CartItem) item).collect(Collectors.toList());
    }

    @Override
    public boolean addToCart(CartItem item) {
        // 计算商品总价
        item.calculateTotalAmount();

        Long userId = UserContext.getUserId();
        String cartKey = getCartKey(userId);
        List<CartItem> cartItems = getUserCartItems();
        
        // 检查是否已存在相同SKU的商品
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(cartItem -> cartItem.getSkuId().equals(item.getSkuId()))
                .findFirst();
        
        if (existingItem.isPresent()) {
            // 存在则更新数量
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
            cartItem.calculateTotalAmount();
            
            // 移除旧商品，添加更新后的商品
            redisTemplate.delete(cartKey);
            cartItems = cartItems.stream()
                    .filter(cartItem1 -> !cartItem1.getSkuId().equals(item.getSkuId()))
                    .collect(Collectors.toList());
        }
        
        cartItems.add(item);
        // 重新保存购物车
        cartItems.forEach(cartItem -> redisTemplate.opsForList().rightPush(cartKey, cartItem));
        return true;
    }

    @Override
    public boolean removeFromCart(String skuId) {
        Long userId = UserContext.getUserId();
        String cartKey = getCartKey(userId);
        List<CartItem> cartItems = getUserCartItems();
        
        // 过滤掉要移除的商品
        List<CartItem> remainingItems = cartItems.stream()
                .filter(item -> !item.getSkuId().equals(skuId))
                .toList();
        
        // 重新保存购物车
        redisTemplate.delete(cartKey);
        remainingItems.forEach(item -> redisTemplate.opsForList().rightPush(cartKey, item));
        return true;
    }

    @Override
    public boolean updateCartItemQuantity(String skuId, Integer quantity) {
        if (quantity <= 0) {
            return removeFromCart(skuId);
        }

        Long userId = UserContext.getUserId();
        String cartKey = getCartKey(userId);
        List<CartItem> cartItems = getUserCartItems();
        
        // 查找并更新指定商品的数量
        Optional<CartItem> itemOpt = cartItems.stream()
                .filter(item -> item.getSkuId().equals(skuId))
                .findFirst();
        
        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            item.setQuantity(quantity);
            item.calculateTotalAmount();
            
            // 重新保存购物车
            redisTemplate.delete(cartKey);
            cartItems.forEach(cartItem -> redisTemplate.opsForList().rightPush(cartKey, cartItem));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCartItemSelection(List<String> skuIds, boolean selected) {
        Long userId = UserContext.getUserId();
        String cartKey = getCartKey(userId);
        List<CartItem> cartItems = getUserCartItems();
        
        boolean updated = false;
        for (CartItem item : cartItems) {
            if (skuIds.contains(item.getSkuId())) {
                item.setSelected(selected);
                updated = true;
            }
        }
        
        if (updated) {
            // 重新保存购物车
            redisTemplate.delete(cartKey);
            cartItems.forEach(cartItem -> redisTemplate.opsForList().rightPush(cartKey, cartItem));
        }
        return updated;
    }

    @Override
    public boolean clearCart() {
        Long userId = UserContext.getUserId();
        String cartKey = getCartKey(userId);
        redisTemplate.delete(cartKey);
        return true;
    }

    @Override
    public List<CartItem> getSelectedCartItems() {
        List<CartItem> cartItems = getUserCartItems();
        return cartItems.stream()
                .filter(CartItem::getSelected)
                .toList();
    }

    @Override
    public Map<String, Object> calculateSelectedTotal() {
        List<CartItem> selectedItems = getSelectedCartItems();
        
        Map<String, Object> result = new HashMap<>();
        
        // 计算总金额
        BigDecimal totalAmount = selectedItems.stream()
                .map(CartItem::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // 计算商品总数
        Integer totalQuantity = selectedItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        
        result.put("totalAmount", totalAmount);
        result.put("totalQuantity", totalQuantity);
        result.put("itemCount", selectedItems.size());
        result.put("selectedItems", selectedItems);
        
        return result;
    }

    /**
     * 获取购物车在Redis中的键
     */
    private String getCartKey(Long userId) {
        return CART_KEY_PREFIX + userId;
    }
}