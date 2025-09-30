package com.cloud.order.controller;

import com.cloud.order.service.AsyncTaskService;
import com.cloud.common.web.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 异步任务控制器
 * 演示如何通过API调用使用线程池执行异步任务
 */
@RestController
@RequestMapping("/async")
@Tag(name = "异步任务", description = "线程池使用示例接口")
public class AsyncTaskController {

    @Autowired
    private AsyncTaskService asyncTaskService;

    /**
     * 批量数据处理示例
     * 这个接口演示了如何使用线程池并行处理多个数据项
     * 适用于需要处理大量数据但又希望快速响应的场景
     */
    @PostMapping("/batch-process")
    @Operation(summary = "批量数据处理", description = "使用线程池并行处理多个数据项")
    public Result<List<String>> batchProcess(@RequestBody List<String> items) {
        long startTime = System.currentTimeMillis();
        List<String> results = asyncTaskService.processBatchData(items);
        long endTime = System.currentTimeMillis();
        
        System.out.println("批量处理完成，耗时: " + (endTime - startTime) + "ms");
        return Result.success(results);
    }

    /**
     * 测试批量处理示例
     * 提供预设的测试数据，方便直接调用查看效果
     */
    @GetMapping("/test-batch")
    @Operation(summary = "测试批量处理", description = "使用预设数据测试线程池批量处理能力")
    public Result<List<String>> testBatchProcess() {
        // 创建10个测试数据项
        List<String> testItems = Arrays.asList(
                "数据项1", "数据项2", "数据项3", "数据项4", "数据项5",
                "数据项6", "数据项7", "数据项8", "数据项9", "数据项10"
        );
        
        return batchProcess(testItems);
    }

    /**
     * 长时间运行任务示例
     * 这个接口演示了如何使用线程池执行不需要立即返回结果的长时间运行任务
     * 适用于日志处理、数据同步等后台任务
     */
    @PostMapping("/long-task")
    @Operation(summary = "长时间运行任务", description = "提交一个长时间运行的异步任务到线程池")
    public Result<String> submitLongTask(@RequestBody String taskName) {
        asyncTaskService.executeLongRunningTask(taskName);
        // 立即返回，不等待任务完成
        return Result.success("任务已提交到线程池，将在后台执行");
    }

    /**
     * 测试长时间任务示例
     * 提供一个简单的GET接口来触发长时间任务
     */
    @GetMapping("/test-long-task")
    @Operation(summary = "测试长时间任务", description = "提交一个测试用的长时间运行任务")
    public Result<String> testLongTask() {
        String taskName = "测试任务-" + System.currentTimeMillis();
        return submitLongTask(taskName);
    }
}