package com.cloud.common.security.webmvc;



import com.cloud.common.security.core.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
/**
 * @author local
 * @date 2025/9/22 17:44
 * @description
 */
@Component
@Slf4j
public class JwtTokenProvider {

    public String generateToken(String username) {
        return JwtUtils.generateToken(username);
    }

    public String getUsernameFromToken(String token) {
        return JwtUtils.getSubjectFromToken(token);
    }

    public boolean validateToken(String token) {
        return JwtUtils.validateToken(token);
    }
}