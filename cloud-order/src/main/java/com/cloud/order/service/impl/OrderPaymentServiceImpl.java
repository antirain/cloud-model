package com.cloud.order.service.impl;

import com.cloud.order.entity.OrderPayment;
import com.cloud.order.mapper.OrderPaymentMapper;
import com.cloud.order.service.OrderPaymentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单支付记录表 服务实现类
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Service
public class OrderPaymentServiceImpl extends ServiceImpl<OrderPaymentMapper, OrderPayment> implements OrderPaymentService {

}
