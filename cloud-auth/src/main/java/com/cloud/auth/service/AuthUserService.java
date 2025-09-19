package com.cloud.auth.service;

import com.cloud.api.system.dto.UserCreateDTO;
import com.cloud.api.system.dto.UserInfoDTO;
import com.cloud.common.result.Result;

/**
 * 认证用户服务接口
 */
public interface AuthUserService {

    /**
     * 用户登录
     */
    Result<String> login(String username, String password);

    /**
     * 用户注册
     */
    Result<String> register(UserCreateDTO authUser);

    /**
     * 根据用户名查询用户
     */
    UserInfoDTO getByUsername(String username);

    /**
     * 刷新令牌
     */
    Result<String> refreshToken(String token);

    /**
     * 退出登录
     */
    Result<Void> logout(String token);
}