package com.cloud.system.constant;

/**
 * 系统管理常量类
 */
public class SystemConstants {

    /**
     * 菜单类型
     */
    public static class MenuType {
        public static final Integer DIRECTORY = 1;  // 目录
        public static final Integer MENU = 2;         // 菜单
        public static final Integer BUTTON = 3;       // 按钮
    }

    /**
     * 状态
     */
    public static class Status {
        public static final Integer DISABLED = 0;  // 禁用
        public static final Integer ENABLED = 1;     // 启用
    }

    /**
     * 角色编码
     */
    public static class RoleCode {
        public static final String ADMIN = "admin";      // 超级管理员
        public static final String USER = "user";        // 普通用户
        public static final String DEVELOPER = "developer"; // 开发者
    }

    /**
     * 菜单编码
     */
    public static class MenuCode {
        public static final String SYSTEM = "system";        // 系统管理
        public static final String USER_MANAGE = "user_manage"; // 用户管理
        public static final String ROLE_MANAGE = "role_manage"; // 角色管理
        public static final String MENU_MANAGE = "menu_manage"; // 菜单管理
    }
}