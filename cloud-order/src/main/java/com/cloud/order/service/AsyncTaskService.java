package com.cloud.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 异步任务服务类
 * 演示如何在项目中使用配置好的线程池处理异步任务
 */
@Service
public class AsyncTaskService {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 处理批量数据任务
     * 这个方法演示了如何使用线程池并行处理大量数据
     * @param items 需要处理的数据列表
     * @return 处理结果列表
     */
    public List<String> processBatchData(List<String> items) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        List<String> results = new ArrayList<>();

        // 将每个数据项提交到线程池进行并行处理
        for (String item : items) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(
                    () -> processItem(item), taskExecutor
            );
            futures.add(future);
        }

        // 收集所有任务的结果
        for (CompletableFuture<String> future : futures) {
            try {
                results.add(future.join()); // 等待任务完成并获取结果
            } catch (Exception e) {
                results.add("处理失败: " + e.getMessage());
            }
        }

        return results;
    }

    /**
     * 处理单个数据项
     * 模拟耗时操作，如网络请求、数据处理等
     * @param item 需要处理的数据项
     * @return 处理结果
     */
    private String processItem(String item) {
        try {
            // 模拟耗时操作
            Thread.sleep(1000);
            String threadName = Thread.currentThread().getName();
            return "已处理: " + item + " (线程: " + threadName + ")";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "处理中断: " + item;
        }
    }

    /**
     * 异步执行长时间运行的任务
     * 这种模式适用于不需要立即返回结果的场景
     * @param taskName 任务名称
     */
    public void executeLongRunningTask(String taskName) {
        taskExecutor.execute(() -> {
            try {
                String threadName = Thread.currentThread().getName();
                System.out.println("开始执行任务: " + taskName + " (线程: " + threadName + ")");
                // 模拟长时间运行的任务
                Thread.sleep(5000);
                System.out.println("任务执行完成: " + taskName + " (线程: " + threadName + ")");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("任务执行中断: " + taskName);
            }
        });
    }
}