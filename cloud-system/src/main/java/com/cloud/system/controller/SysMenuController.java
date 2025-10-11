package com.cloud.system.controller;

import com.cloud.common.web.result.Result;
import com.cloud.system.vo.MenuTree;
import com.cloud.system.entity.SysMenu;
import com.cloud.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author local
 * @since 2025-09-30
 */
@RestController
@RequestMapping("/sys-menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService menuService;


    @GetMapping("/list")
    @Operation(summary = "获取菜单列表", description = "获取所有启用菜单列表")
    public Result<List<SysMenu>> getAllMenus() {
        return Result.success(menuService.getAllEnabledMenus());
    }

    @GetMapping("/{roleId}")
    @Operation(summary = "获取角色菜单", description = "根据角色ID获取角色菜单列表")
    public Result<List<SysMenu>> getRoleMenus(
            @Parameter(description = "角色ID") @PathVariable Long roleId) {
        return Result.success(menuService.getRoleMenus(roleId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户菜单", description = "根据用户ID获取用户菜单列表")
    public Result<List<SysMenu>> getUserMenus(
            @Parameter(description = "用户ID") @PathVariable("userId") Long userId) {
        return Result.success(menuService.getUserMenus(userId));
    }

    @PostMapping("/assign")
    @Operation(summary = "分配菜单", description = "给角色分配菜单")
    public Result<Void> assignMenusToRole(
            @Parameter(description = "角色ID") @RequestParam Long roleId,
            @Parameter(description = "菜单ID列表") @RequestParam List<Long> menuIds) {
         menuService.assignMenusToRole(roleId, menuIds);
        return Result.success();
    }

    @PostMapping("/save")
    @Operation(summary = "保存菜单", description = "新增或更新菜单信息")
    public Result<Void> saveMenu(@RequestBody SysMenu sysMenu) {
        menuService.saveOrUpdate(sysMenu);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜单", description = "批量删除菜单")
    public Result<Void> deleteMenus(@RequestParam List<Long> menuIds) {
        menuService.removeByIds(menuIds);
        return Result.success();
    }
}
