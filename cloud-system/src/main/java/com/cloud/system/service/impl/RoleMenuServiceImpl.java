package com.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.result.Result;
import com.cloud.system.entity.RoleMenu;
import com.cloud.system.mapper.RoleMenuMapper;
import com.cloud.system.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色菜单关联服务实现类
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        List<RoleMenu> roleMenus = this.list(wrapper);
        return roleMenus.stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleMenu> getRoleMenusByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        return this.list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> assignMenusToRole(Long roleId, List<Long> menuIds) {
        // 先删除该角色的所有菜单关联
        this.removeByRoleId(roleId);
        
        // 如果菜单ID列表为空，直接返回成功
        if (menuIds == null || menuIds.isEmpty()) {
            return Result.success();
        }
        
        // 构建角色菜单关联对象列表
        List<RoleMenu> roleMenus = menuIds.stream()
                .map(menuId -> {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(roleId);
                    roleMenu.setMenuId(menuId);
                    return roleMenu;
                })
                .collect(Collectors.toList());
        
        // 批量保存
        this.saveBatch(roleMenus);
        
        return Result.success();
    }

    @Override
    public boolean removeByRoleId(Long roleId) {
        LambdaUpdateWrapper<RoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        return this.remove(wrapper);
    }

    @Override
    public boolean removeByMenuId(Long menuId) {
        LambdaUpdateWrapper<RoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(RoleMenu::getMenuId, menuId);
        return this.remove(wrapper);
    }

    @Override
    public boolean removeByRoleIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return true;
        }
        LambdaUpdateWrapper<RoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(RoleMenu::getRoleId, roleIds);
        return this.remove(wrapper);
    }

    @Override
    public boolean removeByMenuIds(List<Long> menuIds) {
        if (menuIds == null || menuIds.isEmpty()) {
            return true;
        }
        LambdaUpdateWrapper<RoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(RoleMenu::getMenuId, menuIds);
        return this.remove(wrapper);
    }
}