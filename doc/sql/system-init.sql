-- 系统管理初始化数据

-- 插入角色数据
INSERT INTO `cloud_system`.`sys_role` (`id`, `role_name`, `role_code`, `description`, `status`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES
(1, '超级管理员', 'admin', '系统超级管理员，拥有所有权限', 1, NOW(), NOW(), 'system', 'system'),
(2, '普通用户', 'user', '系统普通用户，拥有基础权限', 1, NOW(), NOW(), 'system', 'system'),
(3, '开发者', 'developer', '系统开发者，拥有开发和调试权限', 1, NOW(), NOW(), 'system', 'system');

-- 插入菜单数据
INSERT INTO `cloud_system`.`sys_menu` (`id`, `parent_id`, `menu_name`, `menu_code`, `menu_type`, `path`, `component`, `icon`, `sort`, `status`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES
(1, 0, '系统管理', 'system', 1, '/system', 'Layout', 'system', 1, 1, NOW(), NOW(), 'system', 'system'),
(2, 1, '用户管理', 'user_manage', 2, '/system/user', 'system/user/index', 'user', 1, 1, NOW(), NOW(), 'system', 'system'),
(3, 1, '角色管理', 'role_manage', 2, '/system/role', 'system/role/index', 'role', 2, 1, NOW(), NOW(), 'system', 'system'),
(4, 1, '菜单管理', 'menu_manage', 2, '/system/menu', 'system/menu/index', 'menu', 3, 1, NOW(), NOW(), 'system', 'system'),
(5, 2, '新增用户', 'user_add', 3, '', '', '', 1, 1, NOW(), NOW(), 'system', 'system'),
(6, 2, '编辑用户', 'user_edit', 3, '', '', '', 2, 1, NOW(), NOW(), 'system', 'system'),
(7, 2, '删除用户', 'user_delete', 3, '', '', '', 3, 1, NOW(), NOW(), 'system', 'system'),
(8, 3, '新增角色', 'role_add', 3, '', '', '', 1, 1, NOW(), NOW(), 'system', 'system'),
(9, 3, '编辑角色', 'role_edit', 3, '', '', '', 2, 1, NOW(), NOW(), 'system', 'system'),
(10, 3, '删除角色', 'role_delete', 3, '', '', '', 3, 1, NOW(), NOW(), 'system', 'system'),
(11, 4, '新增菜单', 'menu_add', 3, '', '', '', 1, 1, NOW(), NOW(), 'system', 'system'),
(12, 4, '编辑菜单', 'menu_edit', 3, '', '', '', 2, 1, NOW(), NOW(), 'system', 'system'),
(13, 4, '删除菜单', 'menu_delete', 3, '', '', '', 3, 1, NOW(), NOW(), 'system', 'system');

-- 插入角色菜单关联数据（超级管理员拥有所有权限）
INSERT INTO `cloud_system`.`sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES
(1, 1, 1, NOW(), NOW(), 'system', 'system'),
(2, 1, 2, NOW(), NOW(), 'system', 'system'),
(3, 1, 3, NOW(), NOW(), 'system', 'system'),
(4, 1, 4, NOW(), NOW(), 'system', 'system'),
(5, 1, 5, NOW(), NOW(), 'system', 'system'),
(6, 1, 6, NOW(), NOW(), 'system', 'system'),
(7, 1, 7, NOW(), NOW(), 'system', 'system'),
(8, 1, 8, NOW(), NOW(), 'system', 'system'),
(9, 1, 9, NOW(), NOW(), 'system', 'system'),
(10, 1, 10, NOW(), NOW(), 'system', 'system'),
(11, 1, 11, NOW(), NOW(), 'system', 'system'),
(12, 1, 12, NOW(), NOW(), 'system', 'system'),
(13, 1, 13, NOW(), NOW(), 'system', 'system');

-- 普通用户只有查看权限
INSERT INTO `cloud_system`.`sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES
(14, 2, 1, NOW(), NOW(), 'system', 'system'),
(15, 2, 2, NOW(), NOW(), 'system', 'system');

-- 开发者拥有查看和编辑权限
INSERT INTO `cloud_system`.`sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES
(16, 3, 1, NOW(), NOW(), 'system', 'system'),
(17, 3, 2, NOW(), NOW(), 'system', 'system'),
(18, 3, 3, NOW(), NOW(), 'system', 'system'),
(19, 3, 4, NOW(), NOW(), 'system', 'system'),
(20, 3, 6, NOW(), NOW(), 'system', 'system'),
(21, 3, 9, NOW(), NOW(), 'system', 'system'),
(22, 3, 12, NOW(), NOW(), 'system', 'system');

-- 插入OAuth客户端数据
INSERT INTO `cloud_auth`.`oauth_client` (`id`, `client_id`, `client_secret`, `client_name`, `redirect_uri`, `scope`, `authorized_grant_types`, `access_token_validity`, `refresh_token_validity`, `status`, `description`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES
(1, 'web_client', 'web_secret', 'Web客户端', 'http://localhost:3000/callback', 'read,write', 'authorization_code,refresh_token', 3600, 86400, 1, 'Web应用客户端', NOW(), NOW(), 'system', 'system'),
(2, 'mobile_client', 'mobile_secret', '移动客户端', 'http://localhost:8080/callback', 'read', 'password,refresh_token', 1800, 43200, 1, '移动应用客户端', NOW(), NOW(), 'system', 'system'),
(3, 'admin_client', 'admin_secret', '管理客户端', 'http://localhost:8081/callback', 'read,write,admin', 'authorization_code,password,refresh_token', 7200, 172800, 1, '管理后台客户端', NOW(), NOW(), 'system', 'system');

-- 将超级管理员角色分配给admin用户
INSERT INTO `cloud_system`.`sys_user_role` (`id`, `user_id`, `role_id`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES
(1, 1, 1, NOW(), NOW(), 'system', 'system');