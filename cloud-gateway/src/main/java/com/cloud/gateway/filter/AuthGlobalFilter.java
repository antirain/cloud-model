package com.cloud.gateway.filter;

import com.cloud.common.core.constant.SecurityConstants;
import com.cloud.common.security.util.JwtOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author local
 * @date 2025-09-28
 * @description
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final JwtOperator jwtOperator;
    private final AntPathMatcher matcher = new AntPathMatcher();

    /** 白名单 */
    private static final List<String> WHITE_LIST = List.of(
            "/auth/login",
            "/auth/register",
            "/actuator/**"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 1. 白名单直接放行
        boolean skip = WHITE_LIST.stream().anyMatch(p -> matcher.match(p, path));
        if (skip) {
            return chain.filter(exchange);
        }

        // 2. 取令牌
        String token = getToken(request);
        if (!StringUtils.hasText(token)) {
            log.warn("缺少Token:{}", path);
            return unauthorized(exchange);
        }

        // 3. 验签 & 过期
        try {
            var claims = jwtOperator.validate(token);
            String roles = claims.get(SecurityConstants.ROLE_PERMISSION, String.class);
            String userId = String.valueOf(claims.get(SecurityConstants.DETAILS_USER_ID, Long.class));
            // 4. 把头带下去
            ServerHttpRequest mutated = request.mutate()
                    .header("X-User-Id", userId)
                    .header("X-Role", roles)
                    .build();
            return chain.filter(exchange.mutate().request(mutated).build());

        } catch (Exception e) {
            log.warn("无效Token:{}", e.getMessage());
            return unauthorized(exchange);
        }
    }

    private String getToken(ServerHttpRequest request) {
        String bearer = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        // 可以写 JSON 体
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -100;   // 越早越好
    }
}