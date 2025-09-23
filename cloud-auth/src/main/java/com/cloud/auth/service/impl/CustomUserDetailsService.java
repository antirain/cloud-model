package com.cloud.auth.service.impl;


import com.cloud.api.system.client.SystemUserClient;
import com.cloud.api.system.dto.UserLoginDTO;
import com.cloud.common.entity.SecurityUser;
import com.cloud.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SystemUserClient systemUserClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 👇 1. 远程调用 user-service 获取用户数据
        Result<UserLoginDTO> result = systemUserClient.loadUserByUsername(username);
        if (!result.isSuccess()){
            log.warn("调用 user-service 查询用户失败: {}", result.getMessage());
            throw new UsernameNotFoundException("用户服务异常，请稍后重试");
        }
        UserLoginDTO dto = result.getData();
        if (dto == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        if (dto.getStatus() == 0) {
            throw new DisabledException("用户已被禁用");
        }

        // 👇 2. 构造 SecurityUser（实现了 UserDetails）
        return new SecurityUser(dto);
    }
}
