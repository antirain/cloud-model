package com.cloud.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.api.system.dto.UserCreateDTO;
import com.cloud.api.system.dto.UserLoginDTO;
import com.cloud.api.system.dto.UserUpdateDTO;
import com.cloud.common.web.result.Result;
import com.cloud.system.service.SysUserService;
import com.cloud.system.vo.UserDetailVO;
import com.cloud.system.vo.UserListVO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author local
 * @since 2025-09-30
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    private final SysUserService userService;
    @GetMapping("/page")
    public Result<IPage<UserListVO>> page(
            @Parameter(description = "页码") @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
            @Parameter(description = "每页条数") @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
            @Parameter(description = "用户名") @RequestParam(name = "username",required = false) String username,
            @Parameter(description = "状态") @RequestParam(name = "status",required = false) Integer status
    ) {
        return Result.success(userService.page(pageNo, pageSize,username,status));
    }

    @PostMapping
    public Result<Void> save(@RequestBody UserCreateDTO userCreateDTO) {
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<UserDetailVO> get(@PathVariable Long id) {
        return Result.success();
    }

    @GetMapping("/load-by-username")
    Result<UserLoginDTO> loadUserByUsername(@RequestParam("username") String username){
        return Result.success(userService.loadUserByUsername(username));
    }
}
