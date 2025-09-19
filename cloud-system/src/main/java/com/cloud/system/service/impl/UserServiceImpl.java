package com.cloud.system.service.impl;

import com.cloud.api.system.dto.UserLoginDTO;
import com.cloud.system.entity.User;
import com.cloud.common.result.Result;
import com.cloud.system.mapper.UserMapper;
import com.cloud.system.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.system.util.UserConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final RoleService roleService;
    private final MenuService menuService;
    private final RoleMenuService roleMenuService;
    private final UserRoleService userRoleService;


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

    @Override
    public UserLoginDTO loadUserByUsername(String username) {
        UserLoginDTO user = baseMapper.selectByName(username);
        if (user == null)
            return null;

        // 👇 1. 获取角色 codes
        List<String> roleCodes = user.getRoles().stream()
                .map(SysRole::getRoleCode)
                .collect(Collectors.toList());

        // 👇 2. 获取权限 codes（JOIN 查询）
        Set<String> permissionCodes = new HashSet<>();
        for (SysRole role : user.getRoles()) {
            List<SysMenu> menus = menuRepository.findMenusByRoleId(role.getId());
            for (SysMenu menu : menus) {
                if ("3".equals(menu.getMenuType())) { // 按钮/权限点
                    permissionCodes.add(menu.getMenuCode());
                }
            }
        }

        // 👇 3. 构造 DTO（不是直接转 SecurityUser！）
        UserLoginDTO dto = new UserLoginDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRoles(roleCodes);
        dto.setPermissions(new ArrayList<>(permissionCodes));
        dto.setEnabled(user.getEnabled() != null && user.getEnabled());

        return dto;
    }
}