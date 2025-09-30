package com.cloud.auth.service;

import com.cloud.api.system.dto.UserCreateDTO;
import com.cloud.api.system.dto.UserInfoDTO;
import com.cloud.auth.vo.LoginResultVO;

/**
 * 认证用户服务接口
 */
public interface AuthUserService {

    /**
     * 用户登录
     */
    LoginResultVO login(String username, String password);

    /**
     * 用户注册
     */
    void register(UserCreateDTO authUser);

    /**
     * 根据用户名查询用户
     */
    UserInfoDTO getByUsername(String username);

//    /**
//     * 刷新令牌
//     */
//    String refreshToken(String token);
//
//    /**
//     * 退出登录
//     */
//    void logout(String token);
}