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
 * 角色表
 * </p>
 *
 * @author local
 * @since 2025-09-30
 */
@Getter
@Setter
@TableName("sys_role")
@Schema(name = "SysRole", description = "角色表")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色名称")
    @TableField("role_name")
    private String roleName;

    @Schema(description = "角色编码")
    @TableField("role_code")
    private String roleCode;

    @Schema(description = "描述")
    @TableField("description")
    private String description;

    @Schema(description = "状态 0-禁用 1-启用")
    @TableField("status")
    private Byte status;

    @Schema(description = "版本号")
    @TableField("version")
    @Version
    private Integer version;
}
