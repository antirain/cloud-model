//package com.cloud.common.interceptor;
//
//import com.cloud.common.utils.TokenValidator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * JWT令牌验证拦截器
// * 用于在请求处理前验证令牌
// */
//@Component
//public class JwtTokenInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private TokenValidator tokenValidator;
//
//    // 不需要验证令牌的路径
//    private final List<String> excludePaths = Arrays.asList(
//            "/auth/login",
//            "/auth/register",
//            "/api/token/validate",
//            "/api/token/decode",
//            "/swagger-ui/",
//            "/v3/api-docs"
//    );
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//        // 检查是否为排除路径
//        String requestPath = request.getRequestURI();
//        for (String path : excludePaths) {
//            if (requestPath.startsWith(path)) {
//                return true;
//            }
//        }
//
//        // 获取Authorization头
//        String authHeader = request.getHeader("Authorization");
//        String token = tokenValidator.extractTokenFromHeader(authHeader);
//
//        // 验证令牌
//        if (token != null && tokenValidator.validateToken(token)) {
//            // 令牌有效，可以继续处理请求
//            return true;
//        }
//
//        // 令牌无效，返回401错误
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setContentType("application/json");
//        response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"Invalid or expired token\"}");
//        return false;
//    }
//}