package com.cloud.system.controller;

import com.cloud.common.web.result.Result;
import com.cloud.system.entity.SysRole;
import com.cloud.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author local
 * @since 2025-09-30
 */
@RestController
@RequestMapping("/sys-role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @GetMapping("/list")
    public Result<List<SysRole>> list(){
        return Result.success(roleService.list());
    }
}
