package com.cloud.system.exception;

import com.cloud.common.core.enums.ErrorCode;
import lombok.Getter;

/**
 * @author local
 * @date 2025-09-30
 * @description
 */
@Getter
public enum UserErrorCode implements ErrorCode {
    /**
     * 2000-2099 用户模块错误码
     */
    INSERT_FAIL(2001, "插入失败"),
    UPDATE_FAIL(2002, "更新失败"),
    DELETE_FAIL(2003, "删除失败"),
    ;

    private final int code;
    private final String message;

    UserErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
