package com.cloud.auth.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author local
 * @date 2025/9/22 17:00
 * @description
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InnerTokenRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String requestUrl = template.path();
        log.debug("🔧 为请求 [{}] 添加 inner-call", requestUrl);
        template.header("Internal-Call", "true");
    }

    private boolean matchPath(String pattern, String path) {
        if (pattern.equals(path)) {
            return true;
        }
        if (pattern.endsWith("/**")) {
            String prefix = pattern.substring(0, pattern.length() - 3);
            return path.startsWith(prefix);
        }
        if (pattern.endsWith("/*")) {
            String prefix = pattern.substring(0, pattern.length() - 2);
            return path.startsWith(prefix) && path.substring(prefix.length()).indexOf('/') == -1;
        }
        return false;
    }


}