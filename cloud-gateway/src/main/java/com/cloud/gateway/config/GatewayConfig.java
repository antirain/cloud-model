package com.cloud.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关路由配置
 */
//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("cloud-system", r -> r.path("/system/**")
//                        .uri("lb://cloud-system"))
//                .route("cloud-auth", r -> r.path("/auth/**")
//                        .uri("lb://cloud-auth"))
//                .route("cloud-business", r -> r.path("/business/**")
//                        .uri("lb://cloud-business"))
//                .build();
//    }
//}