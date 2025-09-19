package com.cloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.system.entity.Menu;
import com.cloud.common.result.Result;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取角色菜单列表
     */
    Result<List<Menu>> getRoleMenus(Long roleId);

    /**
     * 分配菜单给角色
     */
    Result<Void> assignMenusToRole(Long roleId, List<Long> menuIds);

    /**
     * 获取菜单树结构
     */
    Result<List<Menu>> getMenuTree();

    /**
     * 获取用户菜单列表
     */
    Result<List<Menu>> getUserMenus(Long userId);

    /**
     * 获取所有启用菜单
     */
    Result<List<Menu>> getAllEnabledMenus();
}