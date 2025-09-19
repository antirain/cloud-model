package com.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.system.entity.UserRole;
import com.cloud.common.result.Result;
import com.cloud.system.mapper.UserRoleMapper;
import com.cloud.system.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关联服务实现类
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        List<UserRole> userRoles = this.list(wrapper);
        return userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRole> getUserRolesByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        return this.list(wrapper);
    }

    @Override
    public List<Long> getUserIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getRoleId, roleId);
        List<UserRole> userRoles = this.list(wrapper);
        return userRoles.stream()
                .map(UserRole::getUserId)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRole> getUserRolesByRoleId(Long roleId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getRoleId, roleId);
        return this.list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> assignRolesToUser(Long userId, List<Long> roleIds) {
        // 先删除该用户的所有角色关联
        this.removeByUserId(userId);
        
        // 如果角色ID列表为空，直接返回成功
        if (roleIds == null || roleIds.isEmpty()) {
            return Result.success();
        }
        
        // 构建用户角色关联对象列表
        List<UserRole> userRoles = roleIds.stream()
                .map(roleId -> {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    return userRole;
                })
                .collect(Collectors.toList());
        
        // 批量保存
        this.saveBatch(userRoles);
        
        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> assignUsersToRole(Long roleId, List<Long> userIds) {
        // 先删除该角色的所有用户关联
        this.removeByRoleId(roleId);
        
        // 如果用户ID列表为空，直接返回成功
        if (userIds == null || userIds.isEmpty()) {
            return Result.success();
        }
        
        // 构建用户角色关联对象列表
        List<UserRole> userRoles = userIds.stream()
                .map(userId -> {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    return userRole;
                })
                .collect(Collectors.toList());
        
        // 批量保存
        this.saveBatch(userRoles);
        
        return Result.success();
    }

    @Override
    public boolean removeByUserId(Long userId) {
        LambdaUpdateWrapper<UserRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        return this.remove(wrapper);
    }

    @Override
    public boolean removeByRoleId(Long roleId) {
        LambdaUpdateWrapper<UserRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserRole::getRoleId, roleId);
        return this.remove(wrapper);
    }

    @Override
    public boolean removeByUserIds(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return true;
        }
        LambdaUpdateWrapper<UserRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(UserRole::getUserId, userIds);
        return this.remove(wrapper);
    }

    @Override
    public boolean removeByRoleIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return true;
        }
        LambdaUpdateWrapper<UserRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(UserRole::getRoleId, roleIds);
        return this.remove(wrapper);
    }

    @Override
    public boolean removeByUserIdAndRoleId(Long userId, Long roleId) {
        LambdaUpdateWrapper<UserRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserRole::getUserId, userId)
               .eq(UserRole::getRoleId, roleId);
        return this.remove(wrapper);
    }
}