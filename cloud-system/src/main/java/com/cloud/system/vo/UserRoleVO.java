package com.cloud.system.vo;

import com.cloud.system.entity.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 用户角色视图对象
 */
@Data
@Schema(description = "用户角色信息")
public class UserRoleVO {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户角色列表")
    private List<SysRole> roles;

    @Schema(description = "角色ID列表")
    private List<Long> roleIds;
}