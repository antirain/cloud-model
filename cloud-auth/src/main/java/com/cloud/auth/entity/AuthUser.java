package com.cloud.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 认证用户实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@Schema(name = "AuthUser", description = "认证用户实体")
public class AuthUser extends BaseEntity {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "状态 0:禁用 1:启用")
    private Integer status;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "角色ID")
    private Long roleId;
}