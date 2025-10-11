package com.cloud.order.controller;

import com.cloud.order.entity.OrderRefund;
import com.cloud.order.enumration.OperatorTypeEnum;
import com.cloud.order.enumration.RefundStatusEnum;
import com.cloud.order.service.RefundService;
import com.cloud.common.web.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @author local
 * @date 2025-09-29
 * @description 退款控制器
 */
@RestController
@RequestMapping("/refund")
@Tag(name = "退款服务", description = "订单退款相关接口")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    /**
     * 创建退款申请
     */
    @PostMapping("/create")
    @Operation(summary = "创建退款申请", description = "创建订单或订单项的退款申请")
    public Result<Long> createRefund(@RequestBody OrderRefund refund) {
        Long refundId = refundService.createRefund(refund);
        return Result.success(refundId);
    }

    /**
     * 获取退款申请详情
     */
    @GetMapping("/detail/{refundId}")
    @Operation(summary = "获取退款申请详情", description = "根据退款申请ID获取退款申请详情")
    public Result<OrderRefund> getRefundById(@PathVariable("refundId") Long refundId) {
        OrderRefund refund = refundService.getRefundById(refundId);
        return Result.success(refund);
    }

    /**
     * 获取订单的所有退款申请
     */
    @GetMapping("/order/{orderId}")
    @Operation(summary = "获取订单的退款申请", description = "获取指定订单的所有退款申请")
    public Result<List<OrderRefund>> getRefundsByOrderId(@PathVariable("orderId") Long orderId) {
        List<OrderRefund> refunds = refundService.getRefundsByOrderId(orderId);
        return Result.success(refunds);
    }

    /**
     * 获取当前登录用户的所有退款申请
     */
    @GetMapping("/user")
    @Operation(summary = "获取用户的退款申请", description = "获取当前登录用户的所有退款申请")
    public Result<List<OrderRefund>> getRefundsByCurrentUser() {
        List<OrderRefund> refunds = refundService.getRefundsByUserId(null);
        return Result.success(refunds);
    }

    /**
     * 处理退款申请（管理员操作）
     */
    @PostMapping("/process/{refundId}")
    @Operation(summary = "处理退款申请", description = "处理退款申请，更新退款状态")
    public Result<Boolean> processRefund(@PathVariable("refundId") Long refundId, 
                                        @RequestBody Map<String, Object> params) {
        Integer status = (Integer) params.get("status");
        Long operatorId = (Long) params.get("operatorId");
        Integer operatorType = (Integer) params.get("operatorType");
        String reason = (String) params.get("reason");

        boolean result = refundService.processRefund(refundId, status, operatorId, operatorType, reason);
        return Result.success(result);
    }

    /**
     * 取消退款申请
     */
    @PostMapping("/cancel/{refundId}")
    @Operation(summary = "取消退款申请", description = "当前登录用户取消自己的退款申请")
    public Result<Boolean> cancelRefund(@PathVariable("refundId") Long refundId) {
        boolean result = refundService.cancelRefund(refundId);
        return Result.success(result);
    }

    /**
     * 申请订单项退款
     */
    @PostMapping("/item")
    @Operation(summary = "申请订单项退款", description = "当前登录用户申请订单中单个商品的退款")
    public Result<Long> refundOrderItem(@RequestBody Map<String, Object> params) {
        Long orderId = Long.valueOf(params.get("orderId").toString());
        Long itemId = Long.valueOf(params.get("itemId").toString());
        BigDecimal refundAmount = new BigDecimal(params.get("refundAmount").toString());
        String refundReason = (String) params.get("refundReason");

        Long refundId = refundService.refundOrderItem(orderId, itemId, refundAmount, refundReason);
        return Result.success(refundId);
    }

    /**
     * 检查订单项是否可以退款
     */
    @GetMapping("/check-item")
    @Operation(summary = "检查订单项是否可以退款", description = "检查指定的订单项是否可以申请退款")
    public Result<Boolean> checkItemRefundable(@RequestParam("orderId") Long orderId,
                                             @RequestParam("itemId") Long itemId) {
        boolean refundable = refundService.checkItemRefundable(orderId, itemId);
        return Result.success(refundable);
    }
}