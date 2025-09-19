package com.cloud.system.controller;

import com.cloud.system.entity.Menu;
import com.cloud.system.service.MenuService;
import com.cloud.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理控制器
 */
@RestController
@RequestMapping("/menu")
@Tag(name = "菜单管理", description = "系统菜单管理接口")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/tree")
    @Operation(summary = "获取菜单树", description = "获取所有菜单的树形结构")
    public Result<List<Menu>> getMenuTree() {
        return menuService.getMenuTree();
    }

    @GetMapping("/list")
    @Operation(summary = "获取菜单列表", description = "获取所有启用菜单列表")
    public Result<List<Menu>> getAllMenus() {
        return menuService.getAllEnabledMenus();
    }

    @GetMapping("/role/{roleId}")
    @Operation(summary = "获取角色菜单", description = "根据角色ID获取角色菜单列表")
    public Result<List<Menu>> getRoleMenus(
            @Parameter(description = "角色ID") @PathVariable Long roleId) {
        return menuService.getRoleMenus(roleId);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户菜单", description = "根据用户ID获取用户菜单列表")
    public Result<List<Menu>> getUserMenus(
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        return menuService.getUserMenus(userId);
    }

    @PostMapping("/assign")
    @Operation(summary = "分配菜单", description = "给角色分配菜单")
    public Result<Void> assignMenusToRole(
            @Parameter(description = "角色ID") @RequestParam Long roleId,
            @Parameter(description = "菜单ID列表") @RequestParam List<Long> menuIds) {
        return menuService.assignMenusToRole(roleId, menuIds);
    }

    @PostMapping("/save")
    @Operation(summary = "保存菜单", description = "新增或更新菜单信息")
    public Result<Void> saveMenu(@RequestBody Menu menu) {
        menuService.saveOrUpdate(menu);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜单", description = "批量删除菜单")
    public Result<Void> deleteMenus(@RequestParam List<Long> menuIds) {
        menuService.removeByIds(menuIds);
        return Result.success();
    }
}