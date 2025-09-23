package com.cloud.system;

import com.cloud.common.config.InternalServiceProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 系统管理服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.cloud.system.mapper")
@ComponentScan({"com.cloud.system", "com.cloud.common"})
@EnableConfigurationProperties(InternalServiceProperties.class)
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}