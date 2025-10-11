package com.cloud.order.mapper;

import com.cloud.order.entity.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单商品详情表 Mapper 接口
 * </p>
 *
 * @author local
 * @since 2025-09-29
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

}
