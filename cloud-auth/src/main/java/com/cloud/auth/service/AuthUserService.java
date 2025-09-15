package com.cloud.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.auth.entity.AuthUser;
import com.cloud.common.result.Result;

/**
 * 认证用户服务接口
 */
public interface AuthUserService extends IService<AuthUser> {

    /**
     * 用户登录
     */
    Result<String> login(String username, String password);

    /**
     * 用户注册
     */
    Result<String> register(AuthUser authUser);

    /**
     * 根据用户名查询用户
     */
    AuthUser getByUsername(String username);

    /**
     * 刷新令牌
     */
    Result<String> refreshToken(String token);

    /**
     * 退出登录
     */
    Result<Void> logout(String token);
}