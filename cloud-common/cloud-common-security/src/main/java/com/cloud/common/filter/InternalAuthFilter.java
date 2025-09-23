package com.cloud.common.filter;

import com.cloud.common.annotation.InternalOnly;
import com.cloud.common.config.InternalServiceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author local
 * @date 2025-09-22
 * @description
 */
@Component
@Slf4j
@Order(1)
public class InternalAuthFilter extends OncePerRequestFilter {

    @Autowired
    private InternalServiceProperties internalServiceProperties;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        // 获取目标方法
        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        if (handlerMethod != null) {
            InternalOnly internalOnly = handlerMethod.getMethodAnnotation(InternalOnly.class);
            if (internalOnly == null) {
                // ❗ 不是标记为内部接口 → 拒绝！即使有 Header
                response.setStatus(403);
                response.getWriter().write("Forbidden: not an internal-only endpoint");
                return;
            }
        }

        String innerCall = request.getHeader("Internal-Call");

        if ("true".equals(innerCall)) {
            log.debug("内部认证通过：{}", requestURI);
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_INTERNAL_SERVICE");
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    "internal-service",
                    null,
                    authorities
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        }

        filterChain.doFilter(request, response);
    }
}