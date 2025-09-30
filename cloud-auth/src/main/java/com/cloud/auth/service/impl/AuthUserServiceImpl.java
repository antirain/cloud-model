package com.cloud.auth.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cloud.api.system.client.SystemUserClient;
import com.cloud.api.system.dto.UserCreateDTO;
import com.cloud.api.system.dto.UserInfoDTO;
import com.cloud.api.system.dto.UserLoginDTO;
import com.cloud.auth.service.AuthUserService;
import com.cloud.auth.vo.LoginResultVO;
import com.cloud.common.security.constant.SecurityConstants;
import com.cloud.common.security.util.JwtOperator;
import com.cloud.common.security.util.PasswordEncoderUtil;
import com.cloud.common.core.exception.BusinessException;
import com.cloud.common.web.result.Result;
import com.cloud.common.core.enums.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author local
 * @date 2025-09-22
 * @description
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {


    private final JwtOperator jwtOperator;

    private final SystemUserClient systemUserClient;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public LoginResultVO login(String username, String password) {
        Result<UserLoginDTO> loginResult = systemUserClient.loadUserByUsername(username);
        if (!loginResult.isSuccess()) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR);
        }
        UserLoginDTO userDetails = loginResult.getData();
        if (!PasswordEncoderUtil.matches(password, userDetails.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR);
        }
        String userKey = IdUtil.fastUUID();

        Map<String, Object> claims = new HashMap<>();

        claims.put(SecurityConstants.USER_KEY, userKey);
        claims.put(SecurityConstants.DETAILS_USER_ID, userDetails.getId());
        claims.put(SecurityConstants.DETAILS_USERNAME, userDetails.getUsername());
        claims.put(SecurityConstants.ROLE_PERMISSION, String.join(",", userDetails.getRoles()));

        String token = jwtOperator.generateToken(username, claims);

        LoginResultVO resultVO = new LoginResultVO();
        resultVO.setId(userDetails.getId());
        resultVO.setUsername(userDetails.getUsername());
        resultVO.setToken(token);
        resultVO.setRole(userDetails.getRoles());
        redisTemplate.opsForValue().set(SecurityConstants.USER_KEY + ":" + userKey, resultVO, jwtOperator.getAccessExpiration(), TimeUnit.MILLISECONDS);

        //TODO 记录登录信息
        return resultVO;
    }

    @Override
    public void register(UserCreateDTO authUser) {
        if (getByUsername(authUser.getUsername()) != null) {
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTS);
        }
        authUser.setPassword(PasswordEncoderUtil.encode(authUser.getPassword()));
        systemUserClient.createUser(authUser);
    }

    @Override
    public UserInfoDTO getByUsername(String username) {
        Result<UserInfoDTO> authUserByUsername = systemUserClient.getAuthUserByUsername(username);
        if (!authUserByUsername.isSuccess()) {
            return null;
        }

        return authUserByUsername.getData();
    }

//    @Override
//    public String refreshToken(String token) {
//
//        if (!jwtOperator.validateToken(token, subject)) {
//            throw new BusinessException(ResultCode.DATA_VALIDATION_FAILED);
//        }
//
//        String username = jwtOperator.getUsernameFromToken(token);
//        UserInfoDTO user = getByUsername(username);
//        if (user == null || user.getStatus() == 0) {
//            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
//        }
//
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", user.getId());
//        claims.put("username", user.getUsername());
////        claims.put("roleId", user.getRoleId());
//
//        return jwtOperator.generateToken(username, claims);
//    }
//
//    @Override
//    public void logout(String token) {
//        // 在实际应用中，可以将token加入黑名单
//        // 这里简单返回成功
//        log.info("logout:" + token);
//        String userKeyFromToken = jwtOperator.getUserKeyFromToken(token);
//        log.info("userKeyFromToken:" + userKeyFromToken);
//        redisTemplate.delete(SecurityConstants.USER_KEY + ":" + userKeyFromToken);
//    }
}