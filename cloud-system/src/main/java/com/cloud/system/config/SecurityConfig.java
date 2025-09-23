package com.cloud.system.config;

import com.cloud.common.filter.InternalAuthFilter;
import com.cloud.common.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * @author local
 * @date 2025-09-22
 * @description
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final InternalAuthFilter internalAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                // 👇 关键！先加 InternalAuthFilter，在 JwtAuthenticationFilter 之前
                .addFilterBefore(internalAuthFilter, UsernamePasswordAuthenticationFilter.class)

                // 👇 授权规则
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )

                // 👇 Session 和异常处理
                .sessionManagement(session -> session
                        .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                            response.getWriter().write("{\"code\":401,\"msg\":\"未授权\"}");
                        })
                );

        return http.build();
    }
}