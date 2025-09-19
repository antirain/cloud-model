package com.cloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.system.entity.Role;
import com.cloud.common.result.Result;

import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService extends IService<Role> {

    /**
     * 获取用户角色列表
     */
    Result<List<Role>> getUserRoles(Long userId);

    /**
     * 分配角色给用户
     */
    Result<Void> assignRolesToUser(Long userId, List<Long> roleIds);

    /**
     * 获取所有启用角色
     */
    Result<List<Role>> getAllEnabledRoles();

    /**
     * 根据角色编码获取角色
     */
    Role getByRoleCode(String roleCode);

    /**
     * 批量删除角色
     */
    Result<Void> deleteRoles(List<Long> roleIds);
}