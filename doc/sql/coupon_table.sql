-- 优惠券表结构
-- 适用系统: cloud-model 微服务架构
-- 版本: 1.0
-- 创建日期: "+new Date().toLocaleDateString()+"

-- ----------------------------
-- 优惠券表 - coupon
-- 存储优惠券的基本信息
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '优惠券ID',
  `coupon_name` varchar(100) NOT NULL COMMENT '优惠券名称',
  `coupon_code` varchar(64) DEFAULT NULL COMMENT '优惠码（针对特定优惠券）',
  `coupon_type` tinyint(4) NOT NULL COMMENT '优惠券类型: 1(满减券), 2(折扣券), 3(代金券), 4(运费券)',
  `discount_value` decimal(10,2) DEFAULT NULL COMMENT '折扣值（满减券为金额，折扣券为折扣比例）',
  `min_spend` decimal(10,2) DEFAULT '0.00' COMMENT '最低消费金额',
  `max_discount` decimal(10,2) DEFAULT NULL COMMENT '最大折扣金额（针对折扣券）',
  `start_time` datetime NOT NULL COMMENT '有效期开始时间',
  `end_time` datetime NOT NULL COMMENT '有效期结束时间',
  `total_quantity` int(11) NOT NULL DEFAULT '0' COMMENT '总发行量',
  `used_quantity` int(11) NOT NULL DEFAULT '0' COMMENT '已使用数量',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态: 1(未开始), 2(进行中), 3(已结束), 4(已停用)',
  `scope_type` tinyint(4) NOT NULL COMMENT '适用范围: 1(全场通用), 2(指定分类), 3(指定商品), 4(指定品牌)',
  `publish_type` tinyint(4) NOT NULL COMMENT '发放方式: 1(手动发放), 2(自动发放), 3(活动领取)',
  `limit_per_user` int(11) NOT NULL DEFAULT '1' COMMENT '每人限领数量',
  `limit_per_order` int(11) NOT NULL DEFAULT '1' COMMENT '每单限用数量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_coupon_code` (`coupon_code`),
  KEY `idx_coupon_type` (`coupon_type`),
  KEY `idx_status` (`status`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- ----------------------------
-- 优惠券适用范围表 - coupon_scope
-- 存储优惠券适用的分类、商品或品牌信息
-- ----------------------------
DROP TABLE IF EXISTS `coupon_scope`;
CREATE TABLE `coupon_scope` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `coupon_id` bigint NOT NULL COMMENT '优惠券ID',
  `scope_type` tinyint(4) NOT NULL COMMENT '范围类型: 2(分类), 3(商品), 4(品牌)',
  `target_id` bigint NOT NULL COMMENT '目标ID(分类ID/商品ID/品牌ID)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_coupon_id` (`coupon_id`),
  KEY `idx_scope_type` (`scope_type`),
  KEY `idx_target_id` (`target_id`),
  CONSTRAINT `fk_coupon_scope_coupon_id` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券适用范围表';

-- ----------------------------
-- 用户优惠券表 - user_coupon
-- 存储用户领取的优惠券信息
-- ----------------------------
DROP TABLE IF EXISTS `user_coupon`;
CREATE TABLE `user_coupon` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `coupon_id` bigint NOT NULL COMMENT '优惠券ID',
  `order_id` bigint DEFAULT NULL COMMENT '使用该优惠券的订单ID',
  `receive_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态: 1(未使用), 2(已使用), 3(已过期), 4(已作废)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_coupon_id` (`coupon_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_status` (`status`),
  CONSTRAINT `fk_user_coupon_coupon_id` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_coupon_order_id` FOREIGN KEY (`order_id`) REFERENCES `order_main` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户优惠券表';

-- ----------------------------
-- 表关系说明
-- ----------------------------
-- 1. coupon (1) <---> coupon_scope (N): 一个优惠券可以有多个适用范围
-- 2. coupon (1) <---> user_coupon (N): 一个优惠券可以被多个用户领取
-- 3. user_coupon (1) <---> order_main (1): 一个用户优惠券最多对应一个订单
-- 4. coupon_usage (N) <---> order_main (1): 一个订单可以使用多个优惠券

-- ----------------------------
-- 优惠券测试数据
-- ----------------------------
INSERT INTO `coupon` (`coupon_name`, `coupon_type`, `discount_value`, `min_spend`, `max_discount`, `start_time`, `end_time`, `total_quantity`, `used_quantity`, `status`, `scope_type`, `publish_type`, `limit_per_user`, `limit_per_order`)
VALUES
('新人专享满100减10元', 1, 10.00, 100.00, NULL, '2023-08-01 00:00:00', '2023-12-31 23:59:59', 1000, 0, 2, 1, 2, 1, 1),
('全场9折优惠券', 2, 9.00, 0.00, 50.00, '2023-08-01 00:00:00', '2023-12-31 23:59:59', 5000, 0, 2, 1, 3, 3, 1),
('电子产品满1000减100元', 1, 100.00, 1000.00, NULL, '2023-08-01 00:00:00', '2023-09-30 23:59:59', 2000, 0, 2, 2, 3, 2, 1),
('无门槛20元代金券', 3, 20.00, 0.00, NULL, '2023-08-15 00:00:00', '2023-08-31 23:59:59', 1000, 0, 2, 1, 1, 1, 1);

-- 为电子产品满1000减100元优惠券添加适用分类
INSERT INTO `coupon_scope` (`coupon_id`, `scope_type`, `target_id`)
VALUES
(3, 2, 1),  -- 电子产品分类
(3, 2, 2),  -- 手机分类
(3, 2, 3);  -- 笔记本电脑分类

-- 为测试用户分配优惠券
INSERT INTO `user_coupon` (`user_id`, `coupon_id`, `receive_time`, `status`)
VALUES
(2, 1, '2023-08-10 10:00:00', 1),
(2, 2, '2023-08-10 10:00:00', 1),
(2, 4, '2023-08-15 09:00:00', 1);