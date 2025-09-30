package com.cloud.product.exception;

import com.cloud.common.core.exception.BusinessException;

/**
 * @author local
 * @date 2025-09-29
 * @description
 */
public class ProductException extends BusinessException {
    public ProductException(Integer code ,String message) {
        super(code, message);
    }
}
