package com.cloud.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.common.annotation.Sensitive;
import com.cloud.common.entity.BaseEntity;
import com.cloud.common.enums.SensitiveType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体
 * @author local
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户实体")
@TableName("sys_user")
@Data
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    @Sensitive(SensitiveType.PHONE)
    private String phone;

    @Schema(description = "状态 0-禁用 1-启用")
    private Integer status;

    @Schema(description = "头像")
    private String avatar;

}