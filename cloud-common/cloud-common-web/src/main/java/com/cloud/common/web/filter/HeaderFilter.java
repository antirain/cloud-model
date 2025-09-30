package com.cloud.common.web.filter;

import com.cloud.common.web.context.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author local
 * @date 2025/9/28 15:41
 * @description
 */
@Component
public class HeaderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String userIdStr = req.getHeader("X-User-Id");
        // 简单格式校验
        if (userIdStr != null && userIdStr.matches("\\d{1,19}")) {
            long userId = Long.parseLong(userIdStr);
            // ThreadLocal<Long>
            UserContext.setUserId(userId);
        } else {
            UserContext.clear();
        }
        try {
            chain.doFilter(req, res);
        } finally {
            UserContext.clear();
        }
    }
}