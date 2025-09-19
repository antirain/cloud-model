package com.cloud.auth.controller;

import com.cloud.common.result.Result;
import com.cloud.common.utils.JwtUtil;
import com.cloud.common.utils.TokenValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 令牌验证控制器
 * 提供令牌验证和解析的API
 */
@RestController
@RequestMapping("/auth/token")
@Tag(name = "令牌管理", description = "JWT令牌验证和解析相关接口")
public class TokenController {

    @Autowired
    private TokenValidator tokenValidator;
    
    @Autowired
    private JwtUtil jwtUtils;

    /**
     * 验证令牌是否有效
     * @param token JWT令牌
     * @return 验证结果
     */
    @GetMapping("/validate")
    @Operation(summary = "验证令牌", description = "验证JWT令牌是否有效")
    public Result<Map<String, Object>> validateToken(@Parameter(description = "JWT令牌") @RequestParam String token) {
        Map<String, Object> result = new HashMap<>();
        boolean isValid = tokenValidator.validateToken(token);
        
        result.put("valid", isValid);
        
        if (isValid) {
            result.put("username", jwtUtils.getUsernameFromToken(token));
            result.put("expiration", jwtUtils.getExpirationDateFromToken(token));
            
            // 检查令牌是否即将过期（30分钟内）
            boolean aboutToExpire = tokenValidator.isTokenAboutToExpire(token, 30);
            result.put("aboutToExpire", aboutToExpire);
            
            // 获取用户ID和角色（如果存在）
            try {
                Long userId = tokenValidator.getUserIdFromToken(token);
                if (userId != null) {
                    result.put("userId", userId);
                }
                
                String[] roles = tokenValidator.getRolesFromToken(token);
                if (roles.length > 0) {
                    result.put("roles", roles);
                }
            } catch (Exception e) {
                // 忽略可选字段的解析错误
            }
        }
        
        return Result.success(result);
    }
    
    /**
     * 从请求头中验证令牌
     * @param authorization Authorization请求头
     * @return 验证结果
     */
    @GetMapping("/validate-header")
    @Operation(summary = "从请求头验证令牌", description = "从Authorization请求头中提取并验证JWT令牌")
    public Result<Map<String, Object>> validateTokenFromHeader(
            @Parameter(description = "Authorization请求头") 
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        
        Map<String, Object> result = new HashMap<>();
        
        String token = tokenValidator.extractTokenFromHeader(authorization);
        if (token == null) {
            result.put("valid", false);
            result.put("error", "Invalid Authorization header");
            return Result.success(result);
        }
        
        return validateToken(token);
    }
    
    /**
     * 解析令牌内容
     * @param token JWT令牌
     * @return 令牌中的声明
     */
    @GetMapping("/decode")
    @Operation(summary = "解析令牌", description = "解析JWT令牌中的声明信息")
    public Result<Map<String, Object>> decodeToken(@Parameter(description = "JWT令牌") @RequestParam String token) {
        try {
            Map<String, Object> claims = tokenValidator.getAllClaimsFromToken(token);
            // 移除敏感信息
            claims.remove("password");
            return Result.success(claims);
        } catch (Exception e) {
            return Result.error("无效的令牌: " + e.getMessage());
        }
    }
}