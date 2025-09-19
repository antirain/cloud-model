package com.cloud.common.util;

/**
 * 安全工具类接口
 * 具体实现由各个业务模块提供
 */
public interface SecurityUtils {
    
    /**
     * 获取当前登录用户ID
     */
    Long getCurrentUserId();
    
    /**
     * 获取当前登录用户名
     */
    String getCurrentUsername();
    
    /**
     * 检查用户是否有指定角色
     */
    boolean hasRole(String role);
    
    /**
     * 检查用户是否有指定权限
     */
    boolean hasAuthority(String authority);
}