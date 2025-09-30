-- 订单服务数据库表结构
-- 适用系统: cloud-model 微服务架构
-- 版本: 1.0
-- 创建日期: " + new Date().toLocaleDateString() + "

-- ----------------------------
-- 订单主表 - order_main
-- 存储订单的基本信息
-- ----------------------------
DROP TABLE IF EXISTS `order_main`;
CREATE TABLE `order_main` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额',
  `actual_pay_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '实际支付金额',
  `shipping_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
  `coupon_id` bigint DEFAULT NULL COMMENT '优惠券ID',
  `discount_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '优惠金额',
  `order_status` tinyint(4) NOT NULL COMMENT '订单状态: 1(待支付), 2(待发货), 3(已发货), 4(已完成), 5(已取消), 6(已关闭)',
  `payment_status` tinyint(4) NOT NULL COMMENT '支付状态: 1(未支付), 2(已支付), 3(已退款), 4(部分退款)',
  `payment_type` tinyint(4) DEFAULT NULL COMMENT '支付方式: 1(支付宝), 2(微信支付), 3(信用卡)',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `shipping_address_id` bigint NOT NULL COMMENT '收货地址ID',
  `consignee_name` varchar(64) NOT NULL COMMENT '收货人姓名',
  `consignee_phone` varchar(20) NOT NULL COMMENT '收货人电话',
  `consignee_address` varchar(255) NOT NULL COMMENT '收货人详细地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '订单备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_order_status` (`order_status`),
  KEY `idx_payment_status` (`payment_status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主表';

-- ----------------------------
-- 订单商品详情表 - order_item
-- 存储订单中包含的商品信息
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单项ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `sku_id` bigint NOT NULL COMMENT '商品SKU ID',
  `product_name` varchar(128) NOT NULL COMMENT '商品名称',
  `sku_name` varchar(255) DEFAULT NULL COMMENT 'SKU名称',
  `product_image` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '购买数量',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品单价',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品总价',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `item_status` tinyint(4) DEFAULT '1' COMMENT '订单项状态: 1(正常), 2(异常), 3(退款中), 4(已退款)',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品详情表';

-- ----------------------------
-- 订单支付记录表 - order_payment
-- 存储订单的支付交易记录
-- ----------------------------
DROP TABLE IF EXISTS `order_payment`;
CREATE TABLE `order_payment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '支付记录ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `transaction_id` bigint DEFAULT NULL COMMENT '第三方支付交易ID',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `currency` varchar(10) NOT NULL DEFAULT 'CNY' COMMENT '货币类型',
  `payment_method` tinyint(4) NOT NULL COMMENT '支付方式: 1(支付宝), 2(微信支付), 3(信用卡)',
  `payment_status` tinyint(4) NOT NULL COMMENT '支付状态: 1(初始), 2(处理中), 3(成功), 4(失败), 5(已退款), 6(已关闭)',
  `payment_time` datetime DEFAULT NULL COMMENT '支付完成时间',
  `ip_address` varchar(50) DEFAULT NULL COMMENT '支付IP地址',
  `payment_channel_data` text COMMENT '支付渠道返回数据(序列化存储)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_transaction_id` (`transaction_id`),
  KEY `idx_payment_status` (`payment_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单支付记录表';

-- ----------------------------
-- 订单状态历史表 - order_status_history
-- 记录订单状态变更历史
-- ----------------------------
DROP TABLE IF EXISTS `order_status_history`;
CREATE TABLE `order_status_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '历史记录ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `old_status` tinyint(4) DEFAULT NULL COMMENT '变更前状态',
  `new_status` tinyint(4) NOT NULL COMMENT '变更后状态',
  `status_change_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '状态变更时间',
  `operator_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `operator_type` tinyint(4) DEFAULT NULL COMMENT '操作人类型: 1(系统), 2(用户), 3(管理员)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_new_status` (`new_status`),
  KEY `idx_status_change_time` (`status_change_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单状态历史表';

-- ----------------------------
-- 表关系说明
-- ----------------------------
-- 1. order_main (1) <---> order_item (N): 一个订单包含多个订单项
-- 2. order_main (1) <---> order_payment (1): 一个订单对应一个支付记录
-- 3. order_main (1) <---> order_status_history (N): 一个订单有多个状态变更历史

-- ----------------------------
-- 索引优化说明
-- ----------------------------
-- 1. 所有表都建立了与订单ID的外键索引，以提高关联查询性能
-- 2. 常用查询字段（如状态、创建时间）都建立了索引
-- 3. 主键使用bigint自增类型(不使用显示宽度参数，该参数在MySQL 8.0中已废弃)，提高查询性能和存储空间效率

-- ----------------------------
-- 数据安全说明
-- ----------------------------
-- 1. 使用逻辑删除(deleted字段)而非物理删除
-- 2. 敏感信息（如收货人电话）建议进行加密存储
-- 3. 重要操作都有操作人记录和备注信息

-- ----------------------------
-- 扩展建议
-- ----------------------------
-- 1. 根据业务需要，可以添加订单日志表记录更详细的操作日志
-- 2. 如需支持分库分表，建议以order_id进行分片
-- 3. 对于高并发场景，可以考虑引入消息队列处理订单状态变更和通知