package com.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cloud.system.entity.SysRoleMenu;
import com.cloud.system.mapper.SysRoleMenuMapper;
import com.cloud.system.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author local
 * @since 2025-09-30
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> roleMenus = this.list(wrapper);
        return roleMenus.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
    }

    @Override
    public List<SysRoleMenu> getRoleMenusByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        return this.list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignMenusToRole(Long roleId, List<Long> menuIds) {
        // 先删除该角色的所有菜单关联
        this.removeByRoleId(roleId);

        // 如果菜单ID列表为空，直接返回成功
        if (menuIds == null || menuIds.isEmpty()) {
            return;
        }

        // 构建角色菜单关联对象列表
        List<SysRoleMenu> roleMenus = menuIds.stream()
                .map(menuId -> {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(roleId);
                    roleMenu.setMenuId(menuId);
                    return roleMenu;
                })
                .collect(Collectors.toList());

        // 批量保存
        this.saveBatch(roleMenus);

    }

    @Override
    public void removeByRoleId(Long roleId) {
        LambdaUpdateWrapper<SysRoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        this.remove(wrapper);
    }

    @Override
    public boolean removeByMenuId(Long menuId) {
        LambdaUpdateWrapper<SysRoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRoleMenu::getMenuId, menuId);
        return this.remove(wrapper);
    }

    @Override
    public boolean removeByRoleIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return true;
        }
        LambdaUpdateWrapper<SysRoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(SysRoleMenu::getRoleId, roleIds);
        return this.remove(wrapper);
    }

    @Override
    public boolean removeByMenuIds(List<Long> menuIds) {
        if (menuIds == null || menuIds.isEmpty()) {
            return true;
        }
        LambdaUpdateWrapper<SysRoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(SysRoleMenu::getMenuId, menuIds);
        return this.remove(wrapper);
    }
}
