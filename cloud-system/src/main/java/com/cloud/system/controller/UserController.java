package com.cloud.system.controller;

import com.cloud.api.system.dto.UserInfoDTO;
import com.cloud.api.system.dto.UserLoginDTO;
import com.cloud.api.system.dto.UserUpdateDTO;
import com.cloud.system.entity.User;
import com.cloud.common.result.Result;
import com.cloud.api.system.dto.UserCreateDTO;
import com.cloud.system.service.UserRoleService;
import com.cloud.system.service.UserService;
import com.cloud.system.util.UserConvertor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.system.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/system/user")
@Tag(name = "用户管理", description = "用户管理相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserConvertor userConvertor;
    @GetMapping("/page")
    @Operation(summary = "分页查询用户")
    public Result<IPage<UserVO>> page(
            @Parameter(description = "页码") @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
            @Parameter(description = "每页条数") @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
            @Parameter(description = "用户名") @RequestParam(name = "username",required = false) String username,
            @Parameter(description = "状态") @RequestParam(name = "status",required = false) Integer status) {

        Page<User> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        
        if (username != null && !username.trim().isEmpty()) {
            wrapper.like(User::getUsername, username);
        }
        if (status != null) {
            wrapper.eq(User::getStatus, status);
        }
        
        wrapper.orderByDesc(User::getCreateTime);
        IPage<User> userPage = userService.page(page, wrapper);
        
        // 将User实体列表转换为UserVO列表
        IPage<UserVO> userVOPage = userPage.convert(userConvertor::toUserVO);
        return Result.success(userVOPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询用户")
    public Result<UserVO> getById(@Parameter(description = "用户ID") @PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        // 将User实体转换为UserVO
        UserVO userVO = userConvertor.toUserVO(user);
        return Result.success(userVO);
    }

    @PostMapping
    @Operation(summary = "新增用户")
    public Result<Long> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        // 将UserCreateDTO转换为User对象
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword());
        user.setNickname(userCreateDTO.getNickname());
        user.setEmail(userCreateDTO.getEmail());
        user.setPhone(userCreateDTO.getPhone());
        user.setStatus(1); // 默认启用
        
        Result<User> result = userService.register(user);
        if (result.isSuccess() && result.getData() != null) {
            return Result.success(result.getData().getId());
        }
        return Result.error(result.getMessage());
    }

    @PutMapping
    @Operation(summary = "修改用户")
    public Result<Boolean> update(@RequestBody UserUpdateDTO userUpdateDTO) {
        if (userUpdateDTO.getId() == null) {
            return Result.error("用户ID不能为空");
        }
        User user = userConvertor.toUser(userUpdateDTO);
        boolean updated = userService.updateById(user);
        return updated ? Result.success(true) : Result.error("修改失败");
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "删除用户")
    public Result<Boolean> removeById(@Parameter(description = "用户ID") @PathVariable Long userId) {
        boolean deleted = userService.removeById(userId);
        return deleted ? Result.success(true) : Result.error("删除失败");
    }

    @PutMapping("/{id}/status/{status}")
    @Operation(summary = "修改用户状态")
    public Result<Void> updateStatus(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Parameter(description = "状态") @PathVariable Integer status) {
        return userService.updateStatus(id, status);
    }

    // 以下是Feign客户端需要的接口实现

    @GetMapping("/info/{username}")
    @Operation(summary = "根据用户名获取用户认证信息")
    public Result<UserInfoDTO> getAuthUserByUsername(@PathVariable("username") String username) {
        User user = userService.getByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        UserInfoDTO userInfoDTO = userConvertor.toUserInfoDTO(user);
        return Result.success(userInfoDTO);
    }

    @GetMapping("/{userId}/info")
    @Operation(summary = "根据用户ID获取用户认证信息")
    public Result<UserInfoDTO> getAuthUserById(@PathVariable("userId") Long userId) {
        User user = userService.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        UserInfoDTO userInfoDTO = userConvertor.toUserInfoDTO(user);
        return Result.success(userInfoDTO);
    }

    @GetMapping("/{userId}/roles")
    @Operation(summary = "根据用户ID获取角色ID列表")
    public Result<List<Long>> getUserRoleIds(@PathVariable("userId") Long userId) {
        User user = userService.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        
        List<Long> roleIds = userRoleService.getRoleIdsByUserId(userId);
        return Result.success(roleIds);
    }

    @GetMapping("/{userId}/permissions")
    @Operation(summary = "根据用户ID获取权限标识列表")
    public Result<List<String>> getUserPermissions(@PathVariable("userId") Long userId) {
        User user = userService.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        
        // 这里简化处理，实际应该从数据库中查询用户权限
        // 为了演示，我们返回一个空列表
        return Result.success(List.of());
    }

    @GetMapping("/exists")
    @Operation(summary = "检查用户名是否存在")
    public Result<Boolean> checkUsernameExists(@RequestParam("username") String username) {
        User user = userService.getByUsername(username);
        return Result.success(user != null);
    }

    @GetMapping("/load-by-username")
    public Result<UserLoginDTO> loadUserByUsername(@RequestParam("username") String username){
        return Result.success(userService.loadUserByUsername(username));
    }
}