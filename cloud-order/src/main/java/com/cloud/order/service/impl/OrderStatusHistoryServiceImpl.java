package com.cloud.order.service.impl;

import com.cloud.order.entity.OrderStatusHistory;
import com.cloud.order.mapper.OrderStatusHistoryMapper;
import com.cloud.order.service.OrderStatusHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单状态历史表 服务实现类
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Service
public class OrderStatusHistoryServiceImpl extends ServiceImpl<OrderStatusHistoryMapper, OrderStatusHistory> implements OrderStatusHistoryService {

}
