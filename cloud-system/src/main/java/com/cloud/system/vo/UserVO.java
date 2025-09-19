package com.cloud.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 用户视图对象
 * 用于前端展示的用户信息，屏蔽内部实现细节
 */
@Data
@Schema(description = "用户信息")
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "状态 0-禁用 1-启用")
    private Integer status;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}