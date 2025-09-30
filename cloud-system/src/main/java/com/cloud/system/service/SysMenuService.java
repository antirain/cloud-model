package com.cloud.system.service;

import com.cloud.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.system.vo.MenuTree;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author local
 * @since 2025-09-30
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> getAllEnabledMenus();

    List<SysMenu> getRoleMenus(Long roleId);

    List<SysMenu> getUserMenus(Long userId);

    void assignMenusToRole(Long roleId, List<Long> menuIds);

    void removeByIds(List<Long> menuIds);

}
