package com.cloud.common.config;// com.cloud.common.security.config.BaseSecurityConfig

import com.cloud.common.filter.InternalAuthFilter;
import com.cloud.common.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author local
 * @date 2025-09-22
 * @description
 */
@Configuration
public class BaseSecurityConfig {

    // ✅ 通用：密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public InternalAuthFilter internalAuthFilter() {
        return new InternalAuthFilter();
    }

    // ✅ 通用：默认拦截规则（低优先级兜底）
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated() // 👈 所有未被放行的请求必须校验 Token
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(CsrfConfigurer::disable);

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(internalAuthFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }

    // ✅ 通用放行：Swagger、Actuator、静态资源等
    @Bean
    @Order(1)
    public SecurityFilterChain commonPermitFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/actuator/**",
                        "/error",
                        "/favicon.ico"
                )
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(CsrfConfigurer::disable);

        return http.build();
    }

}