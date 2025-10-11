-- VForm3 表单模板表
CREATE TABLE IF NOT EXISTS `vform_template` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `form_name` varchar(100) NOT NULL COMMENT '表单名称',
  `form_code` varchar(50) NOT NULL COMMENT '表单编码（唯一标识）',
  `form_desc` varchar(500) DEFAULT NULL COMMENT '表单描述',
  `form_type` tinyint NOT NULL DEFAULT '2' COMMENT '表单类型（1-系统表单，2-自定义表单）',
  `form_json` longtext NOT NULL COMMENT '表单JSON配置',
  `version` int NOT NULL DEFAULT '1' COMMENT '表单版本',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（0-禁用，1-启用）',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '更新人姓名',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标识（0-未删除，1-已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_form_code` (`form_code`),
  KEY `idx_form_type` (`form_type`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='VForm3 表单模板表';

-- 插入示例数据
INSERT INTO `vform_template` (`form_name`, `form_code`, `form_desc`, `form_type`, `form_json`, `version`, `status`, `create_user_id`, `create_user_name`) VALUES
('用户注册表单', 'user_register_form', '用户注册信息收集表单', 2, '{"widgetList":[],"formConfig":{"labelWidth":100,"labelPosition":"right","size":"default","cssCode":"","customClass":"","functions":"","layout":"horizontal","formModel":"formData","formRules":"rules","globalDsv":{}}}', 1, 1, 1, 'admin'),
('订单信息表单', 'order_info_form', '订单信息录入表单', 2, '{"widgetList":[],"formConfig":{"labelWidth":120,"labelPosition":"top","size":"default","cssCode":"","customClass":"","functions":"","layout":"vertical","formModel":"formData","formRules":"rules","globalDsv":{}}}', 1, 1, 1, 'admin');