package com.cloud.order.service.impl;

import com.cloud.order.entity.OrderNotification;
import com.cloud.order.mapper.OrderNotificationMapper;
import com.cloud.order.service.OrderNotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单消息通知表 服务实现类
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Service
public class OrderNotificationServiceImpl extends ServiceImpl<OrderNotificationMapper, OrderNotification> implements OrderNotificationService {

}
