-- VForm3 表单数据表
CREATE TABLE IF NOT EXISTS `vform_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `template_id` bigint NOT NULL COMMENT '表单模板ID',
  `form_code` varchar(50) NOT NULL COMMENT '表单编码',
  `business_id` varchar(100) DEFAULT NULL COMMENT '业务ID（关联具体业务数据）',
  `business_type` varchar(50) DEFAULT NULL COMMENT '业务类型',
  `form_data` longtext NOT NULL COMMENT '表单数据JSON',
  `form_status` tinyint NOT NULL DEFAULT '0' COMMENT '表单状态（0-草稿，1-已提交，2-已审核，3-已驳回）',
  `submit_user_id` bigint DEFAULT NULL COMMENT '提交人ID',
  `submit_user_name` varchar(50) DEFAULT NULL COMMENT '提交人姓名',
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `review_user_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `review_user_name` varchar(50) DEFAULT NULL COMMENT '审核人姓名',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `review_comment` varchar(500) DEFAULT NULL COMMENT '审核意见',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '更新人姓名',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标识（0-未删除，1-已删除）',
  PRIMARY KEY (`id`),
  KEY `idx_template_id` (`template_id`),
  KEY `idx_form_code` (`form_code`),
  KEY `idx_business_id` (`business_id`),
  KEY `idx_form_status` (`form_status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='VForm3 表单数据表';

-- 插入示例数据
INSERT INTO `vform_data` (`template_id`, `form_code`, `business_id`, `business_type`, `form_data`, `form_status`, `submit_user_id`, `submit_user_name`, `submit_time`) VALUES
(1, 'user_register_form', 'user_001', 'user', '{"name": "张三", "email": "zhangsan@example.com", "phone": "13800138000"}', 1, 1, 'admin', NOW()),
(2, 'order_info_form', 'order_001', 'order', '{"orderNo": "ORD20241010001", "amount": 299.99, "product": "笔记本电脑"}', 1, 1, 'admin', NOW());