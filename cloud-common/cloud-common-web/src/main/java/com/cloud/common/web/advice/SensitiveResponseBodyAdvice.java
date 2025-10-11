package com.cloud.common.web.advice;


import com.cloud.common.web.annotation.Sensitive;
import com.cloud.common.web.handler.SensitiveHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
/**
 * @author local
 * @date 2025/9/22 16:08
 * @description
 */
@ControllerAdvice
public class SensitiveResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 对所有接口生效
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return body;
        }
        try {
            process(body);
        } catch (IllegalAccessException e) {
            // 可记录日志，但不要影响主流程
            e.printStackTrace();
        }
        return body;
    }

    private void process(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return;
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Sensitive sensitive = field.getAnnotation(Sensitive.class);
            if (sensitive != null) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value instanceof String) {
                    String masked = SensitiveHandler.mask(
                            (String) value,
                            sensitive.type(),
                            sensitive.maskPattern(),
                            sensitive.maskValue()
                    );
                    field.set(obj, masked);
                }
            }

            // 递归处理嵌套对象（如 List<User>, UserDto 等）
            if (!field.getType().isPrimitive() && !field.getType().getName().startsWith("java.")) {
                field.setAccessible(true);
                Object nested = field.get(obj);
                if (nested != null) {
                    if (nested instanceof Iterable) {
                        for (Object item : (Iterable<?>) nested) {
                            process(item);
                        }
                    } else {
                        process(nested);
                    }
                }
            }
        }
    }
}