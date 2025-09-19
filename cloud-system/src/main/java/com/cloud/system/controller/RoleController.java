package com.cloud.system.controller;

import com.cloud.system.entity.Role;
import com.cloud.system.service.RoleService;
import com.cloud.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("/role")
@Tag(name = "角色管理", description = "系统角色管理接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @Operation(summary = "获取角色列表", description = "获取所有启用角色列表")
    public Result<List<Role>> getAllRoles() {
        return roleService.getAllEnabledRoles();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户角色", description = "根据用户ID获取用户角色列表")
    public Result<List<Role>> getUserRoles(
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        return roleService.getUserRoles(userId);
    }

    @PostMapping("/assign")
    @Operation(summary = "分配角色", description = "给用户分配角色")
    public Result<Void> assignRolesToUser(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "角色ID列表") @RequestParam List<Long> roleIds) {
        return roleService.assignRolesToUser(userId, roleIds);
    }

    @PostMapping("/save")
    @Operation(summary = "保存角色", description = "新增或更新角色信息")
    public Result<Void> saveRole(@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除角色", description = "批量删除角色")
    public Result<Void> deleteRoles(@RequestParam List<Long> roleIds) {
        return roleService.deleteRoles(roleIds);
    }

    @GetMapping("/code/{roleCode}")
    @Operation(summary = "根据编码获取角色", description = "根据角色编码获取角色信息")
    public Result<Role> getByRoleCode(
            @Parameter(description = "角色编码") @PathVariable String roleCode) {
        Role role = roleService.getByRoleCode(roleCode);
        return role != null ? Result.success(role) : Result.error("角色不存在");
    }
}