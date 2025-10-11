package com.cloud.system.vo;

import com.cloud.system.entity.SysMenu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 角色菜单视图对象
 */
@Data
@Schema(description = "角色菜单信息")
public class RoleMenuVO {

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "角色菜单列表")
    private List<SysMenu> menus;

    @Schema(description = "菜单ID列表")
    private List<Long> menuIds;
}