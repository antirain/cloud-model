package com.cloud.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.cloud.common.datasource.entity.BaseEntity;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author local
 * @since 2025-09-30
 */
@Getter
@Setter
@TableName("sys_user")
@Schema(name = "SysUser", description = "用户表")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名")
    @TableField("username")
    private String username;

    @Schema(description = "密码")
    @TableField("password")
    private String password;

    @Schema(description = "昵称")
    @TableField("nickname")
    private String nickname;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "手机号")
    @TableField("phone")
    private String phone;

    @Schema(description = "头像")
    @TableField("avatar")
    private String avatar;

    @Schema(description = "状态 0-禁用 1-启用")
    @TableField("status")
    private Byte status;

    @Schema(description = "版本号")
    @TableField("version")
    @Version
    private Integer version;
}
