package com.cloud.common.core.exception;

import com.cloud.common.core.enums.ErrorCode;
import com.cloud.common.core.enums.ResultCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author local
 * @date 2025-09-29
 * @description
 */
@Setter
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

}