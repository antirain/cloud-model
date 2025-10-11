# Cloud VForm 服务

VForm3 动态表单数据管理服务，用于保存和管理 vform3 生成的自定义表单 JSON 数据。

## 功能特性

### 表单模板管理
- ✅ 表单模板管理（增删改查）
- ✅ 表单版本控制
- ✅ 表单状态管理（启用/禁用）
- ✅ 表单编码唯一性校验
- ✅ 逻辑删除支持

### 表单数据管理
- ✅ 表单数据提交和保存
- ✅ 草稿保存功能
- ✅ 表单数据审核流程
- ✅ 业务数据关联
- ✅ 表单状态跟踪（草稿、已提交、已审核、已驳回）
- ✅ 多维度查询支持

- ✅ RESTful API 接口

## 技术栈

- Spring Boot 3.2.5
- MyBatis Plus 3.5.9
- MySQL 8.0
- Knife4j API 文档
- Spring Cloud 2023.0.1

## 数据库表结构

### 表单模板表 (vform_template)
```sql
CREATE TABLE `vform_template` (
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
  UNIQUE KEY `uk_form_code` (`form_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='VForm3 表单模板表';
```

### 表单数据表 (vform_data)
```sql
CREATE TABLE `vform_data` (
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
```

## API 接口

### 表单模板管理

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/vform/template/list` | 获取启用的表单模板列表 |
| GET | `/vform/template/page` | 分页查询表单模板 |
| GET | `/vform/template/{id}` | 根据ID获取表单模板 |
| GET | `/vform/template/code/{formCode}` | 根据编码获取表单模板 |
| POST | `/vform/template` | 创建表单模板 |
| PUT | `/vform/template` | 更新表单模板 |
| DELETE | `/vform/template/{id}` | 删除表单模板 |
| PUT | `/vform/template/status/{id}` | 启用/禁用表单模板 |

### 表单数据管理

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/vform/data/submit` | 提交表单数据 |
| POST | `/vform/data/draft` | 保存草稿 |
| GET | `/vform/data/business/{businessId}` | 根据业务ID获取表单数据 |
| GET | `/vform/data/form/{formCode}` | 根据表单编码获取表单数据列表 |
| GET | `/vform/data/template/{templateId}` | 根据模板ID获取表单数据列表 |
| GET | `/vform/data/page` | 分页查询表单数据 |
| PUT | `/vform/data/review/{id}` | 审核表单数据 |
| DELETE | `/vform/data/{id}` | 删除表单数据 |

## 启动方式

### 1. 本地开发

```bash
# 编译项目
mvn clean compile

# 运行服务
mvn spring-boot:run -pl cloud-vform
```

### 2. Docker 运行

```bash
# 构建镜像
docker build -t cloud-vform:1.0.0 .

# 运行容器
docker run -d -p 9006:9006 --name cloud-vform cloud-vform:1.0.0
```

## 配置说明

服务端口：9006

Nacos 配置：
- 服务名：cloud-vform
- 配置文件：cloud-vform.yaml

## 集成 VForm3

### 1. 获取表单模板并渲染

```javascript
// 获取表单模板
const response = await fetch('/api/vform/template/code/user_register_form');
const template = await response.json();

// 使用 vform3 渲染表单
const form = new VForm(formContainer, template.formJson);
```

### 2. 保存用户填写的表单数据

```javascript
// 用户填写完成后，获取表单数据
const formData = form.getFormData();

// 提交表单数据
const submitData = {
    templateId: template.id,
    formCode: template.formCode,
    businessId: 'user_001', // 关联的业务ID
    businessType: 'user',   // 业务类型
    formData: JSON.stringify(formData),
    formStatus: 1, // 1-已提交，0-草稿
    submitUserId: 123,
    submitUserName: '张三'
};

const submitResponse = await fetch('/api/vform/data/submit', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(submitData)
});

if (submitResponse.ok) {
    console.log('表单数据保存成功');
}
```

### 3. 保存草稿

```javascript
// 保存草稿
const draftData = {
    templateId: template.id,
    formCode: template.formCode,
    businessId: 'user_001',
    businessType: 'user',
    formData: JSON.stringify(formData),
    formStatus: 0, // 0-草稿
    submitUserId: 123,
    submitUserName: '张三'
};

const draftResponse = await fetch('/api/vform/data/draft', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(draftData)
});
```

### 4. 查询已保存的表单数据

```javascript
// 根据业务ID查询表单数据
const dataResponse = await fetch('/api/vform/data/business/user_001');
const savedData = await dataResponse.json();

if (savedData) {
    // 回填表单数据
    form.setFormData(JSON.parse(savedData.formData));
}
```

## 注意事项

1. 表单编码（form_code）必须唯一
2. 表单JSON配置存储在 form_json 字段中
3. 支持表单版本控制，每次更新版本号自动+1
4. 支持逻辑删除，删除后数据不会物理删除