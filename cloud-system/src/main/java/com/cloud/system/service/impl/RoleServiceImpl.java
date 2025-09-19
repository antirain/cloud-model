package com.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.system.mapper.RoleMapper;
import com.cloud.system.service.RoleService;
import com.cloud.system.service.UserRoleService;
import com.cloud.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cloud.system.entity.Role;
import java.util.List;

/**
 * 角色服务实现类
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public Result<List<Role>> getUserRoles(Long userId) {
        List<Long> roleIds = userRoleService.getRoleIdsByUserId(userId);
        
        if (roleIds.isEmpty()) {
            return Result.success(List.of());
        }

        List<Role> roles = roleMapper.selectBatchIds(roleIds);
        return Result.success(roles);
    }

    @Override
    @Transactional
    public Result<Void> assignRolesToUser(Long userId, List<Long> roleIds) {
        return userRoleService.assignRolesToUser(userId, roleIds);
    }

    @Override
    public Result<List<Role>> getAllEnabledRoles() {
        List<Role> roles = roleMapper.selectList(
            new LambdaQueryWrapper<Role>().eq(Role::getStatus, 1)
        );
        return Result.success(roles);
    }

    @Override
    public Role getByRoleCode(String roleCode) {
        return roleMapper.selectOne(
            new LambdaQueryWrapper<Role>().eq(Role::getRoleCode, roleCode)
        );
    }

    @Override
    @Transactional
    public Result<Void> deleteRoles(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Result.success();
        }

        // 检查是否有用户关联这些角色
        List<Long> userIds = userRoleService.getUserIdsByRoleId(roleIds.get(0));
        if (!userIds.isEmpty()) {
            return Result.error("有用户关联这些角色，无法删除");
        }

        roleMapper.deleteBatchIds(roleIds);
        return Result.success();
    }
}