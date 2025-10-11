package com.cloud.system.service;

import com.cloud.system.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 服务类
 * </p>
 *
 * @author local
 * @since 2025-09-30
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 根据角色ID获取菜单ID列表
     */
    List<Long> getMenuIdsByRoleId(Long roleId);

    /**
     * 根据角色ID获取角色菜单关联列表
     */
    List<SysRoleMenu> getRoleMenusByRoleId(Long roleId);

    /**
     * 分配菜单给角色
     */
    void assignMenusToRole(Long roleId, List<Long> menuIds);

    /**
     * 根据角色ID删除角色菜单关联
     */
    void removeByRoleId(Long roleId);

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
