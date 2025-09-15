package com.cloud.system.service.impl;

import com.cloud.common.result.Result;
import com.cloud.system.entity.User;
import com.cloud.system.mapper.UserMapper;
import com.cloud.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return this.getOne(wrapper);
    }

    @Override
    public Result<User> register(User user) {
        // 检查用户名是否存在
        User existingUser = this.getByUsername(user.getUsername());
        if (existingUser != null) {
            return Result.error("用户名已存在");
        }

        // 保存用户
        this.save(user);
        return Result.success("注册成功", user);
    }

    @Override
    public Result<Void> updateStatus(Long userId, Integer status) {
        User user = this.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        user.setStatus(status);
        this.updateById(user);
        return Result.success();
    }
}