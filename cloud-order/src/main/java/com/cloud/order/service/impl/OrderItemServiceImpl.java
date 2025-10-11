package com.cloud.order.service.impl;

import com.cloud.order.entity.OrderItem;
import com.cloud.order.mapper.OrderItemMapper;
import com.cloud.order.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单商品详情表 服务实现类
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
