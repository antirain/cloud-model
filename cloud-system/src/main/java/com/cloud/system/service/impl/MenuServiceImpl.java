package com.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.system.dto.MenuTree;
import com.cloud.system.entity.Menu;
import com.cloud.system.entity.UserRole;
import com.cloud.system.mapper.MenuMapper;
import com.cloud.system.mapper.UserRoleMapper;
import com.cloud.system.service.MenuService;
import com.cloud.system.service.RoleMenuService;
import com.cloud.common.result.Result;
import com.cloud.system.util.MenuConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public Result<List<Menu>> getRoleMenus(Long roleId) {
        List<Long> menuIds = roleMenuService.getMenuIdsByRoleId(roleId);
        
        if (menuIds.isEmpty()) {
            return Result.success(List.of());
        }

        List<Menu> menus = menuMapper.selectBatchIds(menuIds);
        return Result.success(menus);
    }

    @Override
    @Transactional
    public Result<Void> assignMenusToRole(Long roleId, List<Long> menuIds) {
        return roleMenuService.assignMenusToRole(roleId, menuIds);
    }

    @Override
    public Result<List<MenuTree>> getMenuTree() {
        List<Menu> allMenus = menuMapper.selectList(
            new LambdaQueryWrapper<Menu>().eq(Menu::getStatus, 1)
        );
        List<MenuTree> menuTrees = MenuConvertor.toTree(allMenus);
        return Result.success(menuTrees);
    }

    @Override
    public Result<List<MenuTree>> getUserMenus(Long userId) {
        // 获取用户角色
        List<UserRole> userRoles = userRoleMapper.selectList(
            new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId)
        );
        
        if (userRoles.isEmpty()) {
            return Result.success(List.of());
        }

        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .toList();

        // 获取角色对应的菜单ID
        List<Long> menuIds = roleMenuService.getMenuIdsByRoleId(roleIds.get(0));
        for (int i = 1; i < roleIds.size(); i++) {
            menuIds.addAll(roleMenuService.getMenuIdsByRoleId(roleIds.get(i)));
        }
        
        // 去重
        menuIds = menuIds.stream().distinct().collect(Collectors.toList());

        if (menuIds.isEmpty()) {
            return Result.success(List.of());
        }

        List<Menu> menus = menuMapper.selectByIds(menuIds);
        List<MenuTree> menuTrees = MenuConvertor.toTree(menus);
        return Result.success(menuTrees);
    }

    @Override
    public Result<List<Menu>> getAllEnabledMenus() {
        List<Menu> menus = menuMapper.selectList(
            new LambdaQueryWrapper<Menu>().eq(Menu::getStatus, 1)
        );
        return Result.success(menus);
    }
}