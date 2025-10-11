package com.cloud.api.system.client;

import com.cloud.api.system.client.fallback.SystemUserClientFallbackFactory;
import com.cloud.api.system.dto.UserCreateDTO;
import com.cloud.api.system.dto.UserInfoDTO;
import com.cloud.api.system.dto.UserLoginDTO;
import com.cloud.common.web.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务Feign客户端接口
 * 定义系统模块对外提供的用户相关API
 */
@FeignClient(name = "cloud-system", fallbackFactory = SystemUserClientFallbackFactory.class)
public interface SystemUserClient {

    /**
     * 根据用户名获取认证信息
     *
     * @param username 用户名
     * @return 用户认证信息
     */
    @GetMapping("/sys-user/info/{username}")
    Result<UserInfoDTO> getAuthUserByUsername(@PathVariable("username") String username);


    /**
     * 创建用户
     *
     * @param userCreateDTO 用户创建信息
     * @return 用户信息
     */
    @PostMapping("/sys-user")
    Result<Long> createUser(@RequestBody UserCreateDTO userCreateDTO);

    @GetMapping("/sys-user/load-by-username")
    Result<UserLoginDTO> loadUserByUsername(@RequestParam("username") String username);
}