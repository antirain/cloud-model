# 系统管理模块 (cloud-system)

## 模块概述

系统管理模块是云模型系统的核心模块之一，负责用户权限管理、角色管理、菜单管理等系统基础功能。

## 功能特性

### 1. 用户管理
- 用户信息管理
- 用户角色分配
- 用户权限控制

### 2. 角色管理
- 角色CRUD操作
- 角色权限分配
- 角色菜单关联

### 3. 菜单管理
- 菜单树形结构管理
- 菜单权限控制
- 动态菜单生成

### 4. OAuth客户端管理
- 客户端注册管理
- 客户端权限配置
- 令牌有效期管理

## 数据库结构

### 系统管理相关表
- `sys_user` - 用户表
- `sys_role` - 角色表
- `sys_menu` - 菜单表
- `sys_user_role` - 用户角色关联表
- `sys_role_menu` - 角色菜单关联表

### 认证相关表
- `oauth_client` - OAuth客户端表
- `oauth_access_token` - 访问令牌表
- `oauth_refresh_token` - 刷新令牌表

## API接口

### 角色管理接口
- `GET /system/role/list` - 获取角色列表
- `GET /system/role/user/{userId}` - 获取用户角色
- `POST /system/role/assign` - 分配角色给用户
- `POST /system/role/save` - 保存角色
- `DELETE /system/role/delete` - 删除角色

### 菜单管理接口
- `GET /system/menu/tree` - 获取菜单树
- `GET /system/menu/list` - 获取菜单列表
- `GET /system/menu/role/{roleId}` - 获取角色菜单
- `GET /system/menu/user/{userId}` - 获取用户菜单
- `POST /system/menu/assign` - 分配菜单给角色
- `POST /system/menu/save` - 保存菜单
- `DELETE /system/menu/delete` - 删除菜单

## 使用说明

### 初始化数据
运行 `system-init.sql` 脚本初始化系统基础数据：

```sql
-- 在MySQL中执行
source doc/sql/system-init.sql
```

### 默认角色
- **超级管理员** (admin) - 拥有所有权限
- **普通用户** (user) - 基础查看权限
- **开发者** (developer) - 开发和调试权限

### 默认用户
- 用户名: admin
- 密码: 123456
- 角色: 超级管理员

## 开发指南

### 实体类
- `Role` - 角色实体
- `Menu` - 菜单实体
- `UserRole` - 用户角色关联实体
- `RoleMenu` - 角色菜单关联实体
- `OAuthClient` - OAuth客户端实体

### 服务类
- `RoleService` - 角色服务
- `MenuService` - 菜单服务
- `OAuthClientService` - OAuth客户端服务

### 工具类
- `SecurityUtils` - 安全工具类，提供用户认证相关功能

## 技术栈

- **框架**: Spring Boot 3.x
- **数据库**: MyBatis-Plus
- **安全**: Spring Security
- **文档**: Swagger/OpenAPI 3.0
- **数据库**: MySQL 8.0

## 最佳实践

### 1. 权限控制
使用注解方式进行权限控制：

```java
@PreAuthorize("hasRole('admin')")
@GetMapping("/admin/users")
public Result<List<User>> getAllUsers() {
    // 只有管理员才能访问
}
```

### 2. 菜单权限
通过角色-菜单关联实现细粒度的权限控制

### 3. 用户会话
使用JWT令牌进行用户会话管理

## 注意事项

1. 系统初始化后请及时修改默认密码
2. 生产环境请禁用Swagger文档
3. 定期更新客户端密钥
4. 合理设置令牌有效期

## 扩展开发

### 添加新角色
1. 在 `sys_role` 表中添加角色记录
2. 在 `sys_role_menu` 表中配置角色权限
3. 在代码中使用角色编码进行权限控制

### 添加新菜单
1. 在 `sys_menu` 表中添加菜单记录
2. 配置菜单的父子关系
3. 在 `sys_role_menu` 表中配置角色权限

### 自定义权限
可以通过扩展 `SecurityUtils` 类来实现更复杂的权限控制逻辑。