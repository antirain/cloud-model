package com.cloud.system.controller;

import com.cloud.common.result.Result;
import com.cloud.system.entity.User;
import com.cloud.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户管理相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/page")
    @Operation(summary = "分页查询用户")
    public Result<IPage<User>> page(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNo,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "用户名") @RequestParam(required = false) String username,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status) {

        Page<User> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        
        if (username != null && !username.trim().isEmpty()) {
            wrapper.like(User::getUsername, username);
        }
        if (status != null) {
            wrapper.eq(User::getStatus, status);
        }
        
        wrapper.orderByDesc(User::getCreateTime);
        IPage<User> userPage = userService.page(page, wrapper);
        return Result.success(userPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询用户")
    public Result<User> getById(@Parameter(description = "用户ID") @PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.success(user);
    }

    @PostMapping
    @Operation(summary = "新增用户")
    public Result<User> save(@RequestBody User user) {
        return userService.register(user);
    }

    @PutMapping
    @Operation(summary = "修改用户")
    public Result<User> update(@RequestBody User user) {
        if (user.getId() == null) {
            return Result.error("用户ID不能为空");
        }
        
        boolean updated = userService.updateById(user);
        if (updated) {
            return Result.success(userService.getById(user.getId()));
        }
        return Result.error("修改失败");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public Result<Void> delete(@Parameter(description = "用户ID") @PathVariable Long id) {
        boolean deleted = userService.removeById(id);
        if (deleted) {
            return Result.success();
        }
        return Result.error("删除失败");
    }

    @PutMapping("/{id}/status/{status}")
    @Operation(summary = "修改用户状态")
    public Result<Void> updateStatus(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Parameter(description = "状态") @PathVariable Integer status) {
        return userService.updateStatus(id, status);
    }
}