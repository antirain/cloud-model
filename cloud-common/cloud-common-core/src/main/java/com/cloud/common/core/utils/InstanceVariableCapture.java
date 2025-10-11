package com.cloud.common.core.utils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author local
 * @date 2025/10/9 9:38
 * @description
 */
public class InstanceVariableCapture {
    //商品
    public record Product(String name) {}
    //订单
    public record Order(List<Product> products) {}
    //用户
    public record User(String name, List<Order> orders) {}

    public static List<User> users;

    static {
        List<Product> p1 = List.of(
                new Product("iPhone 15"),
                new Product("AirPods Pro")
        );
        List<Product> p2 = List.of(
                new Product("MacBook Pro")
        );
        List<Order> o1 = List.of(
                new Order(p1),
                new Order(p2)
        );
        User zhangsan = new User("张三", o1);
        List<Product> lisiProducts1 = List.of(
                new Product("iPad Air"),
                new Product("Apple Watch")
        );
        List<Order> o2 = List.of(
                new Order(lisiProducts1)
        );
        User lisi = new User("李四", o2);
        User wangwu = new User("王五", List.of());
        // 所有用户
        users = List.of(zhangsan, lisi, wangwu) ;
    }

    public static void main(String[] args) {

//        List<String> list = users.stream().flatMap(user -> user.orders().stream())
//                .flatMap(order -> order.products().stream())
//                .map(Product::name)
//                .toList();
//        System.out.println(list);
//        Singleton instance = Singleton.INSTANCE;
//        instance.print();


        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException e) { }
            return 10;
        });
        future.orTimeout(1, TimeUnit.SECONDS).exceptionally(ex -> {
            System.out.println("任务执行超时...");
            return null ;
        });
        future.join() ;
    }
}