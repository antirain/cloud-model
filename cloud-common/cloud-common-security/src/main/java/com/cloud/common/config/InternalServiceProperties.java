package com.cloud.common.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 内部服务通信安全配置
 * - token: 内部认证令牌
 * - serverPaths: 服务端需要保护的路径（system-service 使用）
 * - clientTargets: 客户端需要自动加 Token 的目标路径（auth-service 使用）
 */
@Data
@ConfigurationProperties(prefix = "security.internal")
public class InternalServiceProperties {

    /**
     * 内部服务认证令牌
     */
    private String token;

    /**
     * 【服务端】需要内部认证的接口路径（Ant 风格）
     */
    private List<String> protectedPaths;

}