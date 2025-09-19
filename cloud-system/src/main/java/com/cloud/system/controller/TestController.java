package com.cloud.system.controller;

import com.cloud.common.result.Result;
import com.cloud.system.service.RoleMenuService;
import com.cloud.system.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测试控制器 - 用于验证RoleMenu和UserRole功能
 */
@RestController
@RequestMapping("/test")
@Tag(name = "测试接口", description = "用于验证RoleMenu和UserRole功能")
public class TestController {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/role/{roleId}/menus")
    @Operation(summary = "获取角色的菜单ID列表")
    public Result<List<Long>> getRoleMenuIds(
            @Parameter(description = "角色ID") @PathVariable Long roleId) {
        List<Long> menuIds = roleMenuService.getMenuIdsByRoleId(roleId);
        return Result.success(menuIds);
    }

    @PostMapping("/role/{roleId}/menus")
    @Operation(summary = "分配菜单给角色")
    public Result<Void> assignMenusToRole(
            @Parameter(description = "角色ID") @PathVariable Long roleId,
            @Parameter(description = "菜单ID列表") @RequestBody List<Long> menuIds) {
        return roleMenuService.assignMenusToRole(roleId, menuIds);
    }

    @GetMapping("/user/{userId}/roles")
    @Operation(summary = "获取用户的角色ID列表")
    public Result<List<Long>> getUserRoleIds(
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        List<Long> roleIds = userRoleService.getRoleIdsByUserId(userId);
        return Result.success(roleIds);
    }

    @PostMapping("/user/{userId}/roles")
    @Operation(summary = "分配角色给用户")
    public Result<Void> assignRolesToUser(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Parameter(description = "角色ID列表") @RequestBody List<Long> roleIds) {
        return userRoleService.assignRolesToUser(userId, roleIds);
    }

    @PostMapping("/role/{roleId}/users")
    @Operation(summary = "分配用户给角色")
    public Result<Void> assignUsersToRole(
            @Parameter(description = "角色ID") @PathVariable Long roleId,
            @Parameter(description = "用户ID列表") @RequestBody List<Long> userIds) {
        return userRoleService.assignUsersToRole(roleId, userIds);
    }
}