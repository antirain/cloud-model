-- 测试数据SQL脚本
-- 适用系统: cloud-model 微服务架构
-- 版本: 1.0
-- 创建日期: "+new Date().toLocaleDateString()+"

-- 注意：运行此脚本前，请确保已经创建了相应的表结构

-- ----------------------------
-- 商品服务测试数据
-- ----------------------------

-- 商品分类测试数据
INSERT INTO category (category_name, parent_id, level, sort_order, icon, status) VALUES
('电子产品', 0, 1, 1, '/icons/electronics.png', 1),
('手机', 1, 2, 1, '/icons/phone.png', 1),
('笔记本电脑', 1, 2, 2, '/icons/laptop.png', 1),
('家用电器', 0, 1, 2, '/icons/appliance.png', 1),
('厨房电器', 4, 2, 1, '/icons/kitchen.png', 1);

-- 商品测试数据
INSERT INTO product (product_name, description, main_image, image_urls, category_id, brand_id, status) VALUES
('测试智能手机', '高性能智能手机，6.7英寸全面屏，128GB存储', '/images/phone1.jpg', '/images/phone1.jpg,/images/phone1-2.jpg', 2, 1, 1),
('测试笔记本电脑', '轻薄笔记本，16GB内存，512GB SSD', '/images/laptop1.jpg', '/images/laptop1.jpg,/images/laptop1-2.jpg', 3, 2, 1),
('测试电饭煲', '智能电饭煲，5L大容量，多功能烹饪', '/images/rice-cooker.jpg', '/images/rice-cooker.jpg,/images/rice-cooker-2.jpg', 5, 3, 1);

-- 商品SKU测试数据
INSERT INTO product_sku (product_id, sku_name, attributes, price, cost_price, market_price, stock, sales_volume, sku_image, status) VALUES
(1, '测试智能手机-黑色-128GB', '{"颜色":"黑色","存储":"128GB"}', 4999.00, 4000.00, 5299.00, 100, 0, '/images/phone1-black.jpg', 1),
(1, '测试智能手机-白色-128GB', '{"颜色":"白色","存储":"128GB"}', 4999.00, 4000.00, 5299.00, 100, 0, '/images/phone1-white.jpg', 1),
(2, '测试笔记本电脑-灰色-16GB-512GB', '{"颜色":"灰色","内存":"16GB","硬盘":"512GB"}', 6999.00, 5500.00, 7499.00, 50, 0, '/images/laptop1-gray.jpg', 1),
(3, '测试电饭煲-白色-5L', '{"颜色":"白色","容量":"5L"}', 399.00, 300.00, 459.00, 200, 0, '/images/rice-cooker-white.jpg', 1);

-- ----------------------------
-- 订单服务测试数据
-- ----------------------------

-- 订单主表测试数据
INSERT INTO order_main (user_id, total_amount, actual_pay_amount, shipping_fee, coupon_id, discount_amount, order_status, payment_status, payment_type, payment_time, shipping_address_id, consignee_name, consignee_phone, consignee_address, remark)
VALUES
(2, 5398.00, 5398.00, 0.00, NULL, 0.00, 4, 2, 1, '2023-08-15 14:30:00', 1, '测试用户', '17386073391', '测试地址：上海市浦东新区张江高科技园区', '请尽快发货'),
(2, 7098.00, 7098.00, 0.00, NULL, 0.00, 3, 2, 2, '2023-08-20 10:15:00', 1, '测试用户', '17386073391', '测试地址：上海市浦东新区张江高科技园区', NULL),
(2, 399.00, 399.00, 0.00, NULL, 0.00, 2, 2, 1, '2023-08-22 09:45:00', 1, '测试用户', '17386073391', '测试地址：上海市浦东新区张江高科技园区', NULL);

-- 订单商品详情表测试数据
INSERT INTO order_item (order_id, product_id, sku_id, product_name, sku_name, product_image, quantity, price, total_amount, item_status)
VALUES
(1, 1, 1, '测试智能手机', '测试智能手机-黑色-128GB', '/images/phone1.jpg', 1, 4999.00, 4999.00, 1),
(2, 2, 3, '测试笔记本电脑', '测试笔记本电脑-灰色-16GB-512GB', '/images/laptop1.jpg', 1, 6999.00, 6999.00, 1),
(3, 3, 4, '测试电饭煲', '测试电饭煲-白色-5L', '/images/rice-cooker.jpg', 1, 399.00, 399.00, 1);

-- 订单支付记录表测试数据
INSERT INTO order_payment (order_id, transaction_id, amount, currency, payment_method, payment_status, payment_time, ip_address, payment_channel_data)
VALUES
(1, 10001, 5398.00, 'CNY', 1, 3, '2023-08-15 14:30:00', '192.168.1.100', '{"trade_no":"2023081514300001","gateway":"alipay"}'),
(2, 10002, 7098.00, 'CNY', 2, 3, '2023-08-20 10:15:00', '192.168.1.100', '{"trade_no":"2023082010150001","gateway":"wechat"}'),
(3, 10003, 399.00, 'CNY', 1, 3, '2023-08-22 09:45:00', '192.168.1.100', '{"trade_no":"2023082209450001","gateway":"alipay"}');

-- 订单状态历史表测试数据
INSERT INTO order_status_history (order_id, old_status, new_status, status_change_time, operator_id, operator_type, remark)
VALUES
(1, NULL, 1, '2023-08-15 14:25:00', 2, 2, '订单创建'),
(1, 1, 2, '2023-08-15 14:30:00', 2, 2, '支付成功'),
(1, 2, 3, '2023-08-16 10:00:00', 1, 3, '订单发货'),
(1, 3, 4, '2023-08-18 15:30:00', 2, 2, '订单签收'),
(2, NULL, 1, '2023-08-20 10:10:00', 2, 2, '订单创建'),
(2, 1, 2, '2023-08-20 10:15:00', 2, 2, '支付成功'),
(2, 2, 3, '2023-08-21 09:00:00', 1, 3, '订单发货'),
(3, NULL, 1, '2023-08-22 09:40:00', 2, 2, '订单创建'),
(3, 1, 2, '2023-08-22 09:45:00', 2, 2, '支付成功');

-- 订单物流信息表测试数据
INSERT INTO order_logistics (order_id, logistics_company, tracking_number, logistics_status, sender_name, sender_phone, sender_address, receiver_name, receiver_phone, receiver_address, shipping_time, delivery_time, sign_time, last_logistics_info)
VALUES
(1, '顺丰速运', 'SF1234567890', 4, '商家名称', '10086', '上海市徐汇区科技园区', '测试用户', '17386073391', '测试地址：上海市浦东新区张江高科技园区', '2023-08-16 10:00:00', '2023-08-18 14:00:00', '2023-08-18 15:30:00', '【上海市】已签收，签收人：本人'),
(2, '京东物流', 'JD1234567890', 2, '商家名称', '10086', '上海市徐汇区科技园区', '测试用户', '17386073391', '测试地址：上海市浦东新区张江高科技园区', '2023-08-21 09:00:00', NULL, NULL, '【上海市】快递员已揽收');

-- 订单评价表测试数据
INSERT INTO order_review (order_id, item_id, product_id, sku_id, user_id, rating, content, image_urls, video_url, review_type, is_anonymous, status, likes_count, reply_content, reply_time)
VALUES
(1, 1, 1, 1, 2, 5, '手机很好用，拍照清晰，运行流畅，值得购买！', '/images/review/1-1.jpg,/images/review/1-2.jpg', NULL, 2, 0, 2, 10, '感谢您的好评，我们会继续努力提供更好的产品和服务！', '2023-08-19 10:00:00');

-- 订单退款申请表测试数据
-- 这里创建一个已完成的退款申请
INSERT INTO order_refund (order_id, item_id, user_id, refund_amount, currency, refund_reason, refund_type, refund_status, application_time, process_time, refund_finish_time, reject_reason, operator_id, operator_type, proof_images, transaction_id)
VALUES
(1, 1, 2, 500.00, 'CNY', '商品轻微划痕', 1, 6, '2023-08-19 11:00:00', '2023-08-19 14:00:00', '2023-08-19 16:00:00', NULL, 1, 3, '/images/refund/proof1.jpg', 10004);

-- 优惠券使用记录表测试数据
-- 这里创建一个假设使用了优惠券的订单记录
INSERT INTO coupon_usage (order_id, coupon_id, user_id, coupon_code, discount_amount, usage_status, use_time)
VALUES
(1, 1, 2, 'COUPON2023', 0.00, 1, '2023-08-15 14:30:00');

-- 订单消息通知表测试数据
INSERT INTO order_notification (order_id, user_id, notification_type, title, content, is_read, read_time)
VALUES
(1, 2, 1, '订单状态更新', '您的订单已发货，请注意查收！', 1, '2023-08-16 11:00:00'),
(1, 2, 1, '订单状态更新', '您的订单已签收，感谢您的购买！', 1, '2023-08-18 16:00:00'),
(2, 2, 1, '订单状态更新', '您的订单已发货，请注意查收！', 0, NULL),
(3, 2, 2, '支付成功通知', '您的订单支付成功，我们会尽快为您发货！', 0, NULL);

-- ----------------------------
-- 数据导入完成说明
-- ----------------------------
-- 1. 已导入商品服务测试数据：分类、商品、SKU
-- 2. 已导入订单服务测试数据：订单、订单项、支付记录、状态历史、物流信息、评价、退款、优惠券使用、通知
-- 3. 所有数据均关联到用户ID=2（测试用户）
-- 4. 数据覆盖了多个状态，便于测试不同业务场景
-- 5. 运行此脚本前请确保所有表已创建完成