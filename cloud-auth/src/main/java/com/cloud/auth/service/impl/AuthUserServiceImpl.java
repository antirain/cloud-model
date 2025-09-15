package com.cloud.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.auth.entity.AuthUser;
import com.cloud.auth.mapper.AuthUserMapper;
import com.cloud.auth.service.AuthUserService;
import com.cloud.common.exception.BusinessException;
import com.cloud.common.result.Result;
import com.cloud.common.result.ResultCode;
import com.cloud.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证用户服务实现类
 */
@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUser> implements AuthUserService {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Result<String> login(String username, String password) {
        AuthUser user = getByUsername(username);
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }

        if (user.getStatus() == 0) {
            return Result.error(ResultCode.USER_DISABLED);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("roleId", user.getRoleId());

        String token = jwtUtils.generateToken(username, claims);
        return Result.success(token);
    }

    @Override
    public Result<String> register(AuthUser authUser) {
        if (getByUsername(authUser.getUsername()) != null) {
            return Result.error(ResultCode.USER_EXIST);
        }

        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        authUser.setStatus(1);
        save(authUser);

        return Result.success("注册成功");
    }

    @Override
    public AuthUser getByUsername(String username) {
        LambdaQueryWrapper<AuthUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthUser::getUsername, username);
        return getOne(wrapper);
    }

    @Override
    public Result<String> refreshToken(String token) {
        if (!jwtUtils.validateToken(token)) {
            return Result.error(ResultCode.TOKEN_INVALID);
        }

        String username = jwtUtils.getUsernameFromToken(token);
        AuthUser user = getByUsername(username);
        if (user == null || user.getStatus() == 0) {
            return Result.error(ResultCode.USER_NOT_FOUND);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("roleId", user.getRoleId());

        String newToken = jwtUtils.generateToken(username, claims);
        return Result.success(newToken);
    }

    @Override
    public Result<Void> logout(String token) {
        // 在实际应用中，可以将token加入黑名单
        // 这里简单返回成功
        return Result.success();
    }
}