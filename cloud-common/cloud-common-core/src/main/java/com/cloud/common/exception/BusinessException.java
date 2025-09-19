package com.cloud.common.exception;

import com.cloud.common.result.ResultCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常类
 */
@Setter
@Getter
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

}