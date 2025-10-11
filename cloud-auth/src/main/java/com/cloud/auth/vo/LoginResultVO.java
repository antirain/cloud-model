package com.cloud.auth.vo;

import lombok.Data;

import java.util.List;

/**
 * @author local
 * @date 2025/9/26 11:27
 * @description
 */
@Data
public class LoginResultVO {

    private Long id;

    private String username;

    private String token;

    private List<String> role;

}
