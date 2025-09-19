package com.cloud.auth.service.impl;

import com.cloud.api.system.client.SystemUserClient;
import com.cloud.api.system.dto.UserCreateDTO;
import com.cloud.api.system.dto.UserInfoDTO;
import com.cloud.auth.service.AuthUserService;
import com.cloud.common.entity.SecurityUser;
import com.cloud.common.result.Result;
import com.cloud.common.result.ResultCode;
import com.cloud.common.util.SecurityUtils;
import com.cloud.common.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证用户服务实现类
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {


    @Autowired
    private JwtTokenProvider jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SystemUserClient systemUserClient;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Result<String> login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.getId());
        claims.put("username", userDetails.getUsername());
        claims.put("roleId", userDetails.getRoles());

        String token = jwtUtil.generateToken(username, claims);
        return Result.success(token);
    }

    @Override
    public Result<String> register(UserCreateDTO authUser) {
        if (getByUsername(authUser.getUsername()) != null) {
            return Result.error(ResultCode.DATA_ALREADY_EXISTS);
        }

        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        systemUserClient.createUser(authUser);
        return Result.success("注册成功");
    }

    @Override
    public UserInfoDTO getByUsername(String username) {
        Result<UserInfoDTO> authUserByUsername = systemUserClient.getAuthUserByUsername(username);
        if (authUserByUsername.getCode() != 200) {
            return null;
        }

        return authUserByUsername.getData();
    }

    @Override
    public Result<String> refreshToken(String token) {
        String subject = securityUtils.getCurrentUsername();
        if (!jwtUtil.validateToken(token,subject)) {
            return Result.error(ResultCode.DATA_VALIDATION_FAILED);
        }

        String username = jwtUtil.getUsernameFromToken(token);
        UserInfoDTO user = getByUsername(username);
        if (user == null || user.getStatus() == 0) {
            return Result.error(ResultCode.DATA_NOT_FOUND);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
//        claims.put("roleId", user.getRoleId());

        String newToken = jwtUtil.generateToken(username, claims);
        return Result.success(newToken);
    }

    @Override
    public Result<Void> logout(String token) {
        // 在实际应用中，可以将token加入黑名单
        // 这里简单返回成功
        return Result.success();
    }
}