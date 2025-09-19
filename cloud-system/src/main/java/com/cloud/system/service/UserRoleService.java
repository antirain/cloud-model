package com.cloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.system.entity.UserRole;
import com.cloud.common.result.Result;

import java.util.List;

/**
 * 用户角色关联服务接口
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 根据用户ID获取角色ID列表
     */
    List<Long> getRoleIdsByUserId(Long userId);

    /**
     * 根据用户ID获取用户角色关联列表
     */
    List<UserRole> getUserRolesByUserId(Long userId);

    /**
     * 根据角色ID获取用户ID列表
     */
    List<Long> getUserIdsByRoleId(Long roleId);

    /**
     * 根据角色ID获取用户角色关联列表
     */
    List<UserRole> getUserRolesByRoleId(Long roleId);

    /**
     * 分配角色给用户
     */
    Result<Void> assignRolesToUser(Long userId, List<Long> roleIds);

    /**
     * 分配用户给角色
     */
    Result<Void> assignUsersToRole(Long roleId, List<Long> userIds);

    /**
     * 根据用户ID删除用户角色关联
     */
    boolean removeByUserId(Long userId);

    /**
     * 根据角色ID删除用户角色关联
     */
    boolean removeByRoleId(Long roleId);

    /**
     * 批量根据用户ID删除用户角色关联
     */
    boolean removeByUserIds(List<Long> userIds);

    /**
     * 批量根据角色ID删除用户角色关联
     */
    boolean removeByRoleIds(List<Long> roleIds);

    /**
     * 根据用户ID和角色ID删除关联
     */
    boolean removeByUserIdAndRoleId(Long userId, Long roleId);
}