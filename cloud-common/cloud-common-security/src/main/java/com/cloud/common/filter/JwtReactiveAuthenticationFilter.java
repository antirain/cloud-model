//package com.cloud.common.filter;
//
//import com.cloud.common.util.JwtTokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.context.SecurityContextImpl;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
///**
// * @author local
// * @date 2025-09-22
// * @description
// */
//@Component
//@RequiredArgsConstructor
//public class JwtReactiveAuthenticationFilter implements WebFilter {
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    // 👇 可配置的免校验路径（也可改为从配置文件读取）
//    private static final List<String> PERMIT_PATHS = List.of(
//            "/auth/login",
//            "/auth/register",
//            "/actuator/health",
//            "/favicon.ico"
//    );
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        String path = exchange.getRequest().getURI().getPath();
//
//        // 👇 路径放行（不校验 Token）
//        if (isPermitPath(path)) {
//            return chain.filter(exchange);
//        }
//
//        // 👇 解析 Token
//        String token = resolveToken(exchange);
//        if (token == null) {
//            return unauthorized(exchange, "Token is missing");
//        }
//
//        // 👇 校验 Token 有效性
//        if (!jwtTokenProvider.validateToken(token, jwtTokenProvider.getUsernameFromToken(token))) {
//            return unauthorized(exchange, "Invalid or expired token");
//        }
//
//        // 👇 获取 Authentication 并设置到 SecurityContext
//        return jwtTokenProvider.getAuthenticationReactive(token)
//                .map(SecurityContextImpl::new)
//                .flatMap(ctx ->
//                        chain.filter(exchange)
//                                .contextWrite(context -> context.put(org.springframework.security.core.context.SecurityContext.class, ctx))
//                )
//                .onErrorResume(e -> {
//                    return unauthorized(exchange, "Authentication failed: " + e.getMessage());
//                });
//    }
//
//    private boolean isPermitPath(String path) {
//        return PERMIT_PATHS.stream().anyMatch(path::startsWith);
//    }
//
//    private String resolveToken(ServerWebExchange exchange) {
//        String bearerToken = exchange.getRequest().getHeaders().getFirst(jwtTokenProvider.getHeader());
//        return jwtTokenProvider.resolveToken(bearerToken);
//    }
//
//    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        exchange.getResponse().getHeaders().add("WWW-Authenticate", "Bearer error=\"" + message + "\"");
//        return exchange.getResponse().setComplete();
//    }
//}