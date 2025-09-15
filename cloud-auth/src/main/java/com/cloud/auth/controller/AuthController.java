package com.cloud.auth.controller;

import com.cloud.auth.entity.AuthUser;
import com.cloud.auth.service.AuthUserService;
import com.cloud.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "认证管理", description = "用户认证相关接口")
public class AuthController {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口")
    public Result<String> login(
            @Parameter(description = "用户名") @RequestParam String username,
            @Parameter(description = "密码") @RequestParam String password) {
        return authUserService.login(username, password);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public Result<String> register(@RequestBody AuthUser authUser) {
        return authUserService.register(authUser);
    }

    @PostMapping("/refresh")
    @Operation(summary = "刷新令牌", description = "刷新JWT令牌")
    public Result<String> refreshToken(
            @Parameter(description = "旧令牌") @RequestHeader("Authorization") String token) {
        // 移除Bearer前缀
        String jwtToken = token.replace("Bearer ", "");
        return authUserService.refreshToken(jwtToken);
    }

    @PostMapping("/logout")
    @Operation(summary = "退出登录", description = "用户退出登录")
    public Result<Void> logout(
            @Parameter(description = "令牌") @RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "");
        return authUserService.logout(jwtToken);
    }

    @GetMapping("/info")
    @Operation(summary = "获取用户信息", description = "根据用户名获取用户信息")
    public Result<AuthUser> getUserInfo(
            @Parameter(description = "用户名") @RequestParam String username) {
        AuthUser user = authUserService.getByUsername(username);
        if (user != null) {
            // 移除密码信息
            user.setPassword(null);
            return Result.success(user);
        }
        return Result.error("用户不存在");
    }
}