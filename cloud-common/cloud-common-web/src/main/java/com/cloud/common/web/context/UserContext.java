package com.cloud.common.web.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author local
 * @date 2025/9/28 15:29
 * @description
 */
public class UserContext {
    private static final TransmittableThreadLocal<Long> USER_ID = new TransmittableThreadLocal<>();

    public static void setUserId(Long id) {
        USER_ID.set(id);
    }

    public static Long getUserId() {
        return USER_ID.get();
    }

    public static void clear() {
        USER_ID.remove();
    }
}