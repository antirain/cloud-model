package com.cloud.common.security.webflux;

/**
 * @author local
 * @date 2025/9/22 17:40
 * @description
 */

import com.cloud.common.security.core.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ReactiveJwtTokenProvider {

    public Mono<String> generateToken(String username) {
        return Mono.fromSupplier(() -> JwtUtils.generateToken(username));
    }

    public Mono<String> getUsernameFromToken(String token) {
        return Mono.fromSupplier(() -> JwtUtils.getSubjectFromToken(token));
    }

    public Mono<Boolean> validateToken(String token) {
        return Mono.fromSupplier(() -> JwtUtils.validateToken(token));
    }
}