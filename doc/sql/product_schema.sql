-- 商品分类表
CREATE TABLE IF NOT EXISTS category (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID（顶级分类为0或null）',
    level INT NOT NULL COMMENT '分类层级',
    sort_order INT DEFAULT 0 COMMENT '排序号',
    icon VARCHAR(255) DEFAULT NULL COMMENT '分类图标',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '分类状态：1(启用)、0(禁用)',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_level (level),
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='商品分类表';

-- 商品表
CREATE TABLE IF NOT EXISTS product (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    product_name VARCHAR(200) NOT NULL COMMENT '商品名称',
    description TEXT COMMENT '商品描述',
    main_image VARCHAR(255) DEFAULT NULL COMMENT '商品主图',
    image_urls TEXT COMMENT '商品图片列表（多个图片URL用逗号分隔）',
    category_id BIGINT NOT NULL COMMENT '商品分类ID',
    brand_id BIGINT DEFAULT NULL COMMENT '品牌ID',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '商品状态：1(在售)、2(下架)、3(删除)',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_category_id (category_id),
    INDEX idx_brand_id (brand_id),
    INDEX idx_status (status),
    FULLTEXT INDEX idx_product_name_description (product_name, description)
) ENGINE=InnoDB COMMENT='商品表';

-- 商品SKU表
CREATE TABLE IF NOT EXISTS product_sku (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'SKU ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    sku_name VARCHAR(200) NOT NULL COMMENT 'SKU名称',
    attributes JSON COMMENT 'SKU属性（JSON格式，如{"颜色":"红色","尺码":"M"}）',
    price DECIMAL(10,2) NOT NULL COMMENT '销售价格',
    cost_price DECIMAL(10,2) DEFAULT NULL COMMENT '成本价格',
    market_price DECIMAL(10,2) DEFAULT NULL COMMENT '市场价',
    stock INT NOT NULL DEFAULT 0 COMMENT '库存数量',
    sales_volume INT NOT NULL DEFAULT 0 COMMENT '销量',
    sku_image VARCHAR(255) DEFAULT NULL COMMENT 'SKU图片',
    status TINYINT NOT NULL DEFAULT 1 COMMENT 'SKU状态：1(在售)、2(下架)、3(删除)',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_product_id (product_id),
    INDEX idx_status (status),
    INDEX idx_price (price),
    INDEX idx_stock (stock),
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB COMMENT='商品SKU表';