package com.cloud.system.service.impl;

import com.cloud.system.entity.SysMenu;
import com.cloud.system.entity.SysUserRole;
import com.cloud.system.mapper.SysMenuMapper;
import com.cloud.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.system.service.SysRoleMenuService;
import com.cloud.system.service.SysUserRoleService;
import com.cloud.system.vo.MenuTree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author local
 * @since 2025-09-30
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleMenuService roleMenuService;

    private final SysUserRoleService userRoleService;

    @Override
    public List<SysMenu> getAllEnabledMenus() {
        return List.of();
    }

    @Override
    public List<SysMenu> getRoleMenus(Long roleId) {
        List<Long> menuIds = roleMenuService.getMenuIdsByRoleId(roleId);

        if (menuIds.isEmpty()) {
            return List.of();
        }

        return baseMapper.selectByIds(menuIds);
    }

    @Override
    public List<SysMenu> getUserMenus(Long userId) {
        SysUserRole userRole = userRoleService.getById(userId);
        List<Long> menuIds = roleMenuService.getMenuIdsByRoleId(userRole.getRoleId());

        if (menuIds.isEmpty()) {
            return List.of();
        }

        return baseMapper.selectByIds(menuIds);
    }

    @Override
    public void assignMenusToRole(Long roleId, List<Long> menuIds) {

    }

    @Override
    public void removeByIds(List<Long> menuIds) {

    }
}
