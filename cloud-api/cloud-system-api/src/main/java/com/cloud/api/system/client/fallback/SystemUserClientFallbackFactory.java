package com.cloud.api.system.client.fallback;

import com.cloud.api.system.client.SystemUserClient;
import com.cloud.api.system.dto.UserCreateDTO;
import com.cloud.api.system.dto.UserInfoDTO;
import com.cloud.api.system.dto.UserLoginDTO;
import com.cloud.common.web.result.Result;
import org.springframework.cloud.openfeign.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * UserClient的降级处理工厂
 * 实现FallbackFactory接口，可以获取到异常信息
 * 此降级处理直接放在API接口所在模块，方便维护
 */
@Component
@Slf4j
public class SystemUserClientFallbackFactory implements FallbackFactory<SystemUserClient> {

    @Override
    public SystemUserClient create(Throwable cause) {
        log.error("UserClient远程调用失败: {}", cause.getMessage(), cause);
        
        return new SystemUserClient() {
            @Override
            public Result<UserInfoDTO> getAuthUserByUsername(String username) {
                log.error("根据用户名获取用户认证信息失败: 用户名={}", username, cause);
                return Result.error("用户认证信息获取失败: " + cause.getMessage());
            }

            @Override
            public Result<Long> createUser(UserCreateDTO userCreateDTO) {
                log.error("创建用户失败: {}", userCreateDTO, cause);
                return Result.error("用户创建失败: " + cause.getMessage());
            }

            @Override
            public Result<UserLoginDTO> loadUserByUsername(String username) {
                log.error("创建用户失败: {}", username, cause);
                return Result.error("用户创建失败: " + cause.getMessage());
            }

        };
    }
}