package com.cloud.common.web.annotation;

import com.cloud.common.web.enums.SensitiveType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author local
 * @date 2025-09-22
 * @description 数据脱敏
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sensitive {

    SensitiveType value() default SensitiveType.PHONE;

    @AliasFor("value")
    SensitiveType type() default SensitiveType.PHONE;
    /**
     * 自定义脱敏正则（仅当 type=CUSTOM 时生效）
     * 格式："(regex)" -> "replacement"
     * 示例："(\d{3})\d{4}(\d{4})" -> "$1****$2"
     */
    String maskPattern() default "";

    /**
     * 自定义替换内容（仅当 type=CUSTOM 时生效）
     */
    String maskValue() default "";
}
