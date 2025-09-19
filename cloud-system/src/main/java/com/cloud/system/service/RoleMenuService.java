package com.cloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.system.entity.RoleMenu;
import com.cloud.common.result.Result;

import java.util.List;

/**
 * 角色菜单关联服务接口
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 根据角色ID获取菜单ID列表
     */
    List<Long> getMenuIdsByRoleId(Long roleId);

    /**
     * 根据角色ID获取角色菜单关联列表
     */
    List<RoleMenu> getRoleMenusByRoleId(Long roleId);

    /**
     * 分配菜单给角色
     */
    Result<Void> assignMenusToRole(Long roleId, List<Long> menuIds);

    /**
     * 根据角色ID删除角色菜单关联
     */
    boolean removeByRoleId(Long roleId);

    /**
     * 根据菜单ID删除角色菜单关联
     */
    boolean removeByMenuId(Long menuId);

    /**
     * 批量根据角色ID删除角色菜单关联
     */
    boolean removeByRoleIds(List<Long> roleIds);

    /**
     * 批量根据菜单ID删除角色菜单关联
     */
    boolean removeByMenuIds(List<Long> menuIds);
}