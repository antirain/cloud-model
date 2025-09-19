package com.cloud.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜单实体
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "菜单实体")
@TableName("sys_menu")
@Data
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "菜单编码")
    private String menuCode;

    @Schema(description = "菜单类型 1-目录 2-菜单 3-按钮")
    private Integer menuType;

    @Schema(description = "路由路径")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态 0-禁用 1-启用")
    private Integer status;

    @Schema(description = "父级ID")
    private Long parentId;

    @Schema(description = "子菜单")
    List<Menu> children;
}