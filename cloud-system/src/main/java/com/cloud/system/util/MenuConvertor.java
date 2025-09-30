package com.cloud.system.util;

import com.cloud.system.dto.MenuTree;
import com.cloud.system.entity.SysMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单转换工具类
 */
public class MenuConvertor {

    /**
     * 将菜单列表转换为树形结构
     *
     * @param menuList 菜单列表
     * @return 树形菜单列表
     */
    public static List<MenuTree> toTree(List<SysMenu> menuList) {
        if (menuList == null || menuList.isEmpty()) {
            return new ArrayList<>();
        }

        // 按 parentId 分组
        Map<Long, List<SysMenu>> menuGroup = menuList.stream()
                .collect(Collectors.groupingBy(SysMenu::getParentId));

        // 获取根节点（parentId 为 0 或 null 的菜单）
        List<SysMenu> rootMenus = menuGroup.getOrDefault(0L, new ArrayList<>());

        // 构建树形结构
        return rootMenus.stream()
                .map(menu -> buildTree(menu, menuGroup))
                .collect(Collectors.toList());
    }

    /**
     * 递归构建菜单树
     *
     * @param menu      当前菜单
     * @param menuGroup 菜单分组
     * @return 菜单树节点
     */
    private static MenuTree buildTree(SysMenu menu, Map<Long, List<SysMenu>> menuGroup) {
        MenuTree menuTree = new MenuTree();
        menuTree.setId(menu.getId());
        menuTree.setMenuName(menu.getMenuName());
        menuTree.setMenuCode(menu.getMenuCode());
        menuTree.setMenuType(menu.getMenuType());
        menuTree.setPath(menu.getPath());
        menuTree.setComponent(menu.getComponent());
        menuTree.setIcon(menu.getIcon());
        menuTree.setSort(menu.getSort());
        menuTree.setStatus(menu.getStatus());
        menuTree.setParentId(menu.getParentId());

        // 递归处理子菜单
        List<SysMenu> children = menuGroup.getOrDefault(menu.getId(), new ArrayList<>());
        if (!children.isEmpty()) {
            menuTree.setChildren(
                    children.stream()
                            .map(child -> buildTree(child, menuGroup))
                            .collect(Collectors.toList())
            );
        }

        return menuTree;
    }
}