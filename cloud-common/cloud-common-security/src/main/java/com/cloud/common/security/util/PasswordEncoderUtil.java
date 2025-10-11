package com.cloud.common.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author local
 * @date 2025/9/28 17:19
 * @description
 */
public class PasswordEncoderUtil {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder(10);

    private PasswordEncoderUtil() {}

    /**
     * 加密明文密码
     */
    public static String encode(CharSequence rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /**
     * 验证明文密码与加密后密码是否匹配
     */
    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}
