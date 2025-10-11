-- 订单服务辅助表结构
-- 适用系统: cloud-model 微服务架构
-- 版本: 1.0
-- 创建日期: " + new Date().toLocaleDateString() + "

-- ----------------------------
-- 订单物流信息表 - order_logistics
-- 存储订单的物流配送信息
-- ----------------------------
DROP TABLE IF EXISTS `order_logistics`;
CREATE TABLE `order_logistics` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '物流信息ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `logistics_company` varchar(64) DEFAULT NULL COMMENT '物流公司名称',
  `tracking_number` varchar(64) DEFAULT NULL COMMENT '物流单号',
  `logistics_status` tinyint(4) DEFAULT NULL COMMENT '物流状态: 1(未发货), 2(运输中), 3(已送达), 4(已签收)',
  `sender_name` varchar(64) DEFAULT NULL COMMENT '发件人姓名',
  `sender_phone` varchar(20) DEFAULT NULL COMMENT '发件人电话',
  `sender_address` varchar(255) DEFAULT NULL COMMENT '发件人详细地址',
  `receiver_name` varchar(64) NOT NULL COMMENT '收件人姓名',
  `receiver_phone` varchar(20) NOT NULL COMMENT '收件人电话',
  `receiver_address` varchar(255) NOT NULL COMMENT '收件人详细地址',
  `shipping_time` datetime DEFAULT NULL COMMENT '发货时间',
  `delivery_time` datetime DEFAULT NULL COMMENT '送达时间',
  `sign_time` datetime DEFAULT NULL COMMENT '签收时间',
  `last_logistics_info` varchar(512) DEFAULT NULL COMMENT '最新物流信息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_tracking_number` (`tracking_number`),
  KEY `idx_logistics_status` (`logistics_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单物流信息表';

-- ----------------------------
-- 订单评价表 - order_review
-- 存储用户对订单商品的评价信息
-- ----------------------------
DROP TABLE IF EXISTS `order_review`;
CREATE TABLE `order_review` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `item_id` bigint DEFAULT NULL COMMENT '订单项ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `sku_id` bigint DEFAULT NULL COMMENT '商品SKU ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `rating` tinyint(4) NOT NULL COMMENT '评分(1-5星)',
  `content` text COMMENT '评价内容',
  `image_urls` text COMMENT '评价图片URL，多个图片用逗号分隔',
  `video_url` varchar(255) DEFAULT NULL COMMENT '评价视频URL',
  `review_type` tinyint(4) DEFAULT NULL COMMENT '评价类型: 1(文字), 2(图片), 3(视频)',
  `is_anonymous` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否匿名评价: 0-否, 1-是',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '评价状态: 1(待审核), 2(已通过), 3(已拒绝)',
  `likes_count` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `reply_content` text COMMENT '商家回复内容',
  `reply_time` datetime DEFAULT NULL COMMENT '商家回复时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_item_id` (`item_id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单评价表';

-- ----------------------------
-- 订单退款申请表 - order_refund
-- 存储订单的退款申请信息
-- ----------------------------
DROP TABLE IF EXISTS `order_refund`;
CREATE TABLE `order_refund` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '退款申请ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `item_id` bigint DEFAULT NULL COMMENT '订单项ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `refund_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '退款金额',
  `currency` varchar(10) NOT NULL DEFAULT 'CNY' COMMENT '货币类型',
  `refund_reason` text NOT NULL COMMENT '退款原因',
  `refund_type` tinyint(4) NOT NULL COMMENT '退款类型: 1(仅退款), 2(退货退款)',
  `refund_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '退款状态: 1(待处理), 2(处理中), 3(已同意), 4(已拒绝), 5(退款中), 6(已完成), 7(已关闭)',
  `application_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `process_time` datetime DEFAULT NULL COMMENT '处理时间',
  `refund_finish_time` datetime DEFAULT NULL COMMENT '退款完成时间',
  `reject_reason` text COMMENT '拒绝原因',
  `operator_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `operator_type` tinyint(4) DEFAULT NULL COMMENT '操作人类型: 1(系统), 2(用户), 3(管理员), 4(客服)',
  `proof_images` text COMMENT '凭证图片URL，多个图片用逗号分隔',
  `transaction_id` bigint DEFAULT NULL COMMENT '退款交易ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_item_id` (`item_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_refund_status` (`refund_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单退款申请表';

-- ----------------------------
-- 优惠券使用记录表 - coupon_usage
-- 记录订单使用的优惠券信息
-- ----------------------------
DROP TABLE IF EXISTS `coupon_usage`;
CREATE TABLE `coupon_usage` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '使用记录ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `coupon_id` bigint NOT NULL COMMENT '优惠券ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `coupon_code` varchar(64) DEFAULT NULL COMMENT '优惠券码',
  `discount_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '优惠金额',
  `usage_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '使用状态: 1(已使用), 2(已退款), 3(已过期)',
  `use_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '使用时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_coupon_id` (`coupon_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_usage_status` (`usage_status`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券使用记录表';

-- ----------------------------
-- 订单消息通知表 - order_notification
-- 记录订单相关的消息通知
-- ----------------------------
DROP TABLE IF EXISTS `order_notification`;
CREATE TABLE `order_notification` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `user_id` bigint NOT NULL COMMENT '接收用户ID',
  `notification_type` tinyint(4) NOT NULL COMMENT '通知类型: 1(订单状态变更), 2(支付成功), 3(发货通知), 4(退款状态变更)',
  `title` varchar(128) NOT NULL COMMENT '通知标题',
  `content` text NOT NULL COMMENT '通知内容',
  `is_read` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已读: 0-未读, 1-已读',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_notification_type` (`notification_type`),
  KEY `idx_is_read` (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单消息通知表';

-- ----------------------------
-- 辅助表关系说明
-- ----------------------------
-- 1. order_main (1) <---> order_logistics (1): 一个订单对应一个物流信息
-- 2. order_main (1) <---> order_review (N): 一个订单可以有多条评价
-- 3. order_main (1) <---> order_refund (N): 一个订单可以有多个退款申请
-- 4. order_main (1) <---> coupon_usage (N): 一个订单可以使用多个优惠券
-- 5. order_main (1) <---> order_notification (N): 一个订单可以有多个通知

-- ----------------------------
-- 数据类型规范说明
-- ----------------------------
-- 1. 所有主键字段统一命名为id，类型为bigint自增(不使用显示宽度参数，该参数在MySQL 8.0中已废弃)
-- 2. 所有状态和类型字段使用tinyint(4)类型，提高查询性能和存储空间效率
-- 3. 所有关联ID字段（如order_id, product_id等）使用bigint类型，与对应表的主键类型保持一致

-- ----------------------------
-- 性能优化建议
-- ----------------------------
-- 1. 对于大流量系统，可以考虑将历史数据归档到专门的历史表
-- 2. 对频繁查询的字段组合建立复合索引
-- 3. 考虑引入读写分离架构应对高并发场景
-- 4. 订单相关的统计数据可以考虑使用缓存或定期计算的方式维护

-- ----------------------------
-- 数据一致性保障
-- ----------------------------
-- 1. 建议使用事务确保订单相关表的数据一致性
-- 2. 关键业务操作建议记录操作日志，以便追溯
-- 3. 对于分布式系统，考虑使用最终一致性方案