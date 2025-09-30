package com.cloud.order.service;

import com.cloud.common.web.context.UserContext;
import com.cloud.order.entity.OrderRefund;

import java.math.BigDecimal;
import java.util.List;

/**
 * 退款服务接口
 * 定义订单退款相关的操作方法
 */
public interface RefundService {

    /**
     * 创建退款申请
     * @param refund 退款申请信息
     * @return 退款申请ID
     */
    Long createRefund(OrderRefund refund);

    /**
     * 查询退款申请详情
     * @param refundId 退款申请ID
     * @return 退款申请详情
     */
    OrderRefund getRefundById(Long refundId);

    /**
     * 查询订单的所有退款申请
     * @param orderId 订单ID
     * @return 退款申请列表
     */
    List<OrderRefund> getRefundsByOrderId(Long orderId);

    /**
     * 查询用户的所有退款申请
     * @param userId 用户ID（可为null，null时从UserContext获取）
     * @return 退款申请列表
     */
    List<OrderRefund> getRefundsByUserId(Long userId);

    /**
     * 处理退款申请
     * @param refundId 退款申请ID
     * @param status 处理状态
     * @param operatorId 操作人ID
     * @param operatorType 操作人类型
     * @param reason 处理原因（拒绝时必填）
     * @return 处理结果
     */
    boolean processRefund(Long refundId, Integer status, Long operatorId, Integer operatorType, String reason);

    /**
     * 取消退款申请
     * @param refundId 退款申请ID
     * @return 取消结果
     */
    boolean cancelRefund(Long refundId);

    /**
     * 处理订单项的部分退款
     * @param orderId 订单ID
     * @param itemId 订单项ID
     * @param refundAmount 退款金额
     * @param refundReason 退款原因
     * @return 退款申请ID
     */
    Long refundOrderItem(Long orderId, Long itemId,BigDecimal refundAmount, String refundReason);

    /**
     * 检查订单项是否可以退款
     * @param orderId 订单ID
     * @param itemId 订单项ID
     * @return 检查结果
     */
    boolean checkItemRefundable(Long orderId, Long itemId);
}