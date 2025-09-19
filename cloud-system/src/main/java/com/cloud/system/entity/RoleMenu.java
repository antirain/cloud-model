package com.cloud.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色菜单关联实体
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色菜单关联实体")
@TableName("sys_role_menu")
@Data
public class RoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "菜单ID")
    private Long menuId;

}