package com.cloud.order.service.impl;

import com.cloud.common.web.context.UserContext;
import com.cloud.order.entity.OrderRefund;
import com.cloud.order.enumration.RefundStatusEnum;
import com.cloud.order.service.RefundService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 退款服务实现类
 * 实现退款相关的操作方法
 */
@Service
public class RefundServiceImpl implements RefundService {

    // 模拟数据库存储
    private Map<Long, OrderRefund> refundMap = new HashMap<>();
    private Map<Long, List<OrderRefund>> orderRefundsMap = new HashMap<>();
    private Map<Long, List<OrderRefund>> userRefundsMap = new HashMap<>();
    // 存储每个订单项的已退款金额，用于防止超额退款
    private Map<String, java.math.BigDecimal> orderItemRefundedAmountMap = new HashMap<>();

    @Override
    public Long createRefund(OrderRefund refund) {
        // 生成退款申请ID
        refund.setApplicationTime(LocalDateTime.now());
        refund.setCreateTime(LocalDateTime.now());
        refund.setUpdateTime(LocalDateTime.now());

        // 存储退款申请
        refundMap.put(1l, refund);

        // 更新订单退款列表
        orderRefundsMap.computeIfAbsent(refund.getOrderId(), k -> new ArrayList<>()).add(refund);

        // 更新用户退款列表
        userRefundsMap.computeIfAbsent(refund.getUserId(), k -> new ArrayList<>()).add(refund);

        return 1l;
    }

    @Override
    public OrderRefund getRefundById(Long refundId) {
        return refundMap.get(refundId);
    }

    @Override
    public List<OrderRefund> getRefundsByOrderId(Long orderId) {
        return orderRefundsMap.getOrDefault(orderId, Collections.emptyList());
    }

    @Override
    public List<OrderRefund> getRefundsByUserId(Long userId) {
        // 如果userId为null，则从UserContext获取当前登录用户的ID
        Long actualUserId = UserContext.getUserId();
        return userRefundsMap.getOrDefault(actualUserId, Collections.emptyList());
    }

    @Override
    public boolean processRefund(Long refundId, Integer status, Long operatorId, Integer operatorType, String reason) {
        OrderRefund refund = refundMap.get(refundId);
        if (refund == null) {
            return false;
        }

        // 检查状态是否合法
        if (!isValidStatusTransition(refund.getRefundStatus(), status)) {
            return false;
        }

        // 更新退款状态
        refund.setRefundStatus(status);
        refund.setOperatorId(operatorId);
        refund.setOperatorType(operatorType);
        refund.setProcessTime(LocalDateTime.now());
        refund.setUpdateTime(LocalDateTime.now());

        // 如果是拒绝状态，设置拒绝原因
        if (RefundStatusEnum.REJECTED.getCode().equals(status)) {
            refund.setRejectReason(reason);
        }

        // 如果是成功状态，设置退款完成时间
        if (RefundStatusEnum.SUCCESS.getCode().equals(status)) {
            refund.setRefundFinishTime(LocalDateTime.now());
            // 这里应该调用支付系统的退款接口
            // 模拟支付系统退款
            refund.setTransactionId(10l);
            
            // 如果是订单项退款，更新已退款金额
            if (refund.getItemId() != null) {
                String itemKey = refund.getOrderId() + ":" + refund.getItemId();
                BigDecimal alreadyRefunded = orderItemRefundedAmountMap.getOrDefault(itemKey, BigDecimal.ZERO);
                orderItemRefundedAmountMap.put(itemKey, alreadyRefunded.add(refund.getRefundAmount()));
            }
        }

        return true;
    }

    @Override
    public boolean cancelRefund(Long refundId) {
        // 如果userId为null，则从UserContext获取当前登录用户的ID
        Long actualUserId = UserContext.getUserId();
        
        OrderRefund refund = refundMap.get(refundId);
        if (refund == null || !refund.getUserId().equals(actualUserId)) {
            return false;
        }

        // 只有待处理的退款申请可以取消
        if (!RefundStatusEnum.PENDING.getCode().equals(refund.getRefundStatus())) {
            return false;
        }

        // 更新退款状态为关闭
        refund.setRefundStatus(RefundStatusEnum.CLOSED.getCode());
        refund.setUpdateTime(LocalDateTime.now());

        return true;
    }

    @Override
    public Long refundOrderItem(Long orderId, Long itemId, BigDecimal refundAmount, String refundReason) {
        // 如果userId为null，则从UserContext获取当前登录用户的ID
        Long actualUserId = UserContext.getUserId();
        
        // 检查订单项是否可以退款
        if (!checkItemRefundable(orderId, itemId)) {
            throw new RuntimeException("该订单项不支持退款");
        }

        // 检查退款金额是否合理
        String itemKey = orderId + ":" + itemId;
        BigDecimal alreadyRefunded = orderItemRefundedAmountMap.getOrDefault(itemKey, BigDecimal.ZERO);
        
        // 假设订单项的总金额为1000（实际应该从订单系统获取）
        BigDecimal itemTotalAmount = new BigDecimal("1000");
        
        // 检查是否超额退款
        if (alreadyRefunded.add(refundAmount).compareTo(itemTotalAmount) > 0) {
            throw new RuntimeException("退款金额超过订单项总额，已退款：" + alreadyRefunded + ", 申请退款：" + refundAmount + ", 总额：" + itemTotalAmount);
        }

        // 创建退款申请
        OrderRefund refund = new OrderRefund();
        refund.setOrderId(orderId);
        refund.setItemId(itemId);
        refund.setUserId(actualUserId);
        refund.setRefundAmount(refundAmount);
        refund.setRefundReason(refundReason);

        return createRefund(refund);
    }

    @Override
    public boolean checkItemRefundable(Long orderId, Long itemId) {
        // 实际业务中，这里需要检查订单状态、订单项状态、是否已经退款等
        // 这里简化处理，假设大部分情况都可以退款
        
        // 检查该订单项是否已经有退款申请
        List<OrderRefund> refunds = getRefundsByOrderId(orderId);
        for (OrderRefund refund : refunds) {
            if (itemId != null && itemId.equals(refund.getItemId())) {
                // 已经有退款申请，且状态不是已拒绝或已关闭
                Integer refundStatus = refund.getRefundStatus();
                if (!RefundStatusEnum.REJECTED.getCode().equals(refundStatus) && 
                    !RefundStatusEnum.CLOSED.getCode().equals(refundStatus)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 检查状态转换是否合法
     */
    private boolean isValidStatusTransition(Integer currentStatus, Integer targetStatus) {
        // 定义合法的状态转换
        Map<Integer, List<Integer>> validTransitions = new HashMap<>();
        validTransitions.put(RefundStatusEnum.PENDING.getCode(), Arrays.asList(
                RefundStatusEnum.PROCESSING.getCode(), 
                RefundStatusEnum.REJECTED.getCode(), 
                RefundStatusEnum.CLOSED.getCode()
        ));
        validTransitions.put(RefundStatusEnum.PROCESSING.getCode(), Arrays.asList(
                RefundStatusEnum.APPROVED.getCode(), 
                RefundStatusEnum.REJECTED.getCode()
        ));
        validTransitions.put(RefundStatusEnum.APPROVED.getCode(), Arrays.asList(
                RefundStatusEnum.REFUNDING.getCode()
        ));
        validTransitions.put(RefundStatusEnum.REFUNDING.getCode(), Arrays.asList(
                RefundStatusEnum.SUCCESS.getCode(), 
                RefundStatusEnum.CLOSED.getCode()
        ));

        List<Integer> allowedStatuses = validTransitions.get(currentStatus);
        return allowedStatuses != null && allowedStatuses.contains(targetStatus);
    }
}