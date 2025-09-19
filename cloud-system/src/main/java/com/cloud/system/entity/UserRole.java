package com.cloud.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色关联实体
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户角色关联实体")
@TableName("sys_user_role")
@Data
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "角色ID")
    private Long roleId;

}