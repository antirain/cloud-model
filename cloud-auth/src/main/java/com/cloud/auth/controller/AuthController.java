package com.cloud.auth.controller;

import com.cloud.api.system.dto.UserCreateDTO;
import com.cloud.api.system.dto.UserInfoDTO;
import com.cloud.auth.service.AuthUserService;
import com.cloud.auth.vo.LoginResultVO;
import com.cloud.common.web.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "认证管理", description = "用户认证相关接口")
public class AuthController {

    private final AuthUserService authUserService;

    @Autowired
    public AuthController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口")
    public Result<LoginResultVO> login(
            @Parameter(description = "用户名") @RequestParam("username") @NotBlank(message = "用户名不能为空") String username,
            @Parameter(description = "密码") @RequestParam("password") @NotBlank(message = "密码不能为空") String password) {
        return Result.success(authUserService.login(username, password));
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public Result<Void> register(@RequestBody @Valid UserCreateDTO authUser) {
        authUserService.register(authUser);
        return Result.success();
    }

//    @PostMapping("/refresh")
//    @Operation(summary = "刷新令牌", description = "刷新JWT令牌")
//    public Result<String> refreshToken(
//            @Parameter(description = "旧令牌") @RequestHeader("Authorization") String token) {
//        // 移除Bearer前缀
//        String jwtToken = extractToken(token);
//        return Result.success(authUserService.refreshToken(jwtToken));
//    }
//
//    @PostMapping("/logout")
//    @Operation(summary = "退出登录", description = "用户退出登录")
//    public Result<Void> logout(
//            @Parameter(description = "令牌") @RequestHeader("Authorization") String token) {
//        String jwtToken = extractToken(token);
//        authUserService.logout(jwtToken);
//        return Result.success();
//    }

    @GetMapping("/info")
    @Operation(summary = "获取用户信息", description = "根据用户名获取用户信息")
    public Result<UserInfoDTO> getUserInfo(
            @Parameter(description = "用户名") @RequestParam("username") @NotBlank(message = "用户名不能为空") String username) {
        UserInfoDTO user = authUserService.getByUsername(username);
        if (user != null) {
            // 移除密码信息
            user.setPassword(null);
            return Result.success(user);
        }
        return Result.error("用户不存在");
    }
    
}