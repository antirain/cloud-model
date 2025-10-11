package com.cloud.auth.config;

import com.cloud.common.security.util.JwtOperator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author local
 * @date 2025/9/28 17:02
 * @description
 */
@Configuration
public class JwtConfig {

    @Bean
    public JwtOperator jwtOperator( @Value("${jwt.secret}") String secret,
                             @Value("${jwt.accessExpiration}") Long accessExpiration,
                             @Value("${jwt.refreshExpiration}") Long refreshExpiration,
                             @Value("${jwt.header:Authorization}") String header,
                             @Value("${jwt.prefix:Bearer }") String prefix) {
        return new JwtOperator(secret, accessExpiration,refreshExpiration, header, prefix);

    }
}
