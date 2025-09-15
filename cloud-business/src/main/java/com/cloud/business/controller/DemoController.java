package com.cloud.business.controller;

import com.cloud.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 演示控制器
 */
@RestController
@RequestMapping("/demo")
@Tag(name = "演示接口", description = "业务服务演示接口")
public class DemoController {

    @GetMapping("/hello")
    @Operation(summary = "Hello接口", description = "测试业务服务是否正常运行")
    public Result<String> hello() {
        return Result.success("Hello from Cloud Business Service!");
    }

    @GetMapping("/info")
    @Operation(summary = "服务信息", description = "获取业务服务信息")
    public Result<Object> getServiceInfo() {
        return Result.success("Cloud Business Service - Port: 9003");
    }
}