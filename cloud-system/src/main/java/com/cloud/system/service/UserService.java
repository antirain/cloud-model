package com.cloud.system.service;

import com.cloud.common.result.Result;
import com.cloud.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名获取用户
     */
    User getByUsername(String username);

    /**
     * 注册用户
     */
    Result<User> register(User user);

    /**
     * 修改用户状态
     */
    Result<Void> updateStatus(Long userId, Integer status);
}