# Cloud Model - Spring Cloud 微服务架构示例

这是一个基于Spring Cloud的真实微服务架构示例项目，使用Nacos作为注册中心，MyBatis-Plus作为数据库工具。

## 🏗️ 项目架构

```
cloud-model/
├── cloud-common/          # 公共模块
├── cloud-gateway/         # 网关服务
├── cloud-system/          # 系统管理服务
├── cloud-auth/            # 认证授权服务
├── cloud-business/        # 业务服务
├── doc/                   # 文档目录
├── docker-compose.yml     # Docker编排
└── pom.xml               # 根项目配置
```

## 🚀 技术栈

### 后端技术栈
- **Spring Boot 3.2.5** - 微服务框架
- **Spring Cloud 2023.0.1** - 微服务治理
- **Spring Cloud Alibaba 2023.0.1.0** - 阿里巴巴微服务组件
- **Nacos 2.3.0** - 服务注册与配置中心
- **MyBatis-Plus 3.5.6** - ORM框架
- **MySQL 8.0.33** - 数据库
- **Druid 1.2.20** - 数据库连接池
- **Knife4j 4.3.0** - API文档
- **JWT** - 身份认证
- **Redis** - 缓存

### 部署技术栈
- **Docker & Docker Compose** - 容器化部署
- **OpenJDK 17** - Java运行环境

## 📦 服务说明

| 服务名称 | 端口 | 描述 |
|---------|------|------|
| cloud-gateway | 9000 | 网关服务，统一入口 |
| cloud-system | 9001 | 系统管理服务，用户/角色/菜单管理 |
| cloud-auth | 9002 | 认证授权服务，JWT令牌管理 |
| cloud-business | 9003 | 业务服务，具体业务逻辑 |
| nacos | 8848 | 注册中心和配置中心 |
| mysql | 3306 | MySQL数据库 |
| redis | 6379 | Redis缓存 |

## 🛠️ 环境要求

- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 7.0+
- Docker & Docker Compose

## 🚦 快速开始

### 1. 克隆项目
```bash
git clone [项目地址]
cd cloud-model
```

### 2. 启动基础设施
```bash
# 使用Docker Compose启动所有基础设施
docker-compose up -d nacos mysql redis
```

### 3. 初始化数据库
执行 `doc/sql/init.sql` 中的SQL脚本，创建数据库和表结构。

### 4. 启动微服务
```bash
# 编译所有模块
mvn clean package -DskipTests

# 启动各个服务（按顺序启动）
# 启动网关
java -jar cloud-gateway/target/cloud-gateway-1.0.0.jar

# 启动系统服务
java -jar cloud-system/target/cloud-system-1.0.0.jar

# 启动认证服务
java -jar cloud-auth/target/cloud-auth-1.0.0.jar

# 启动业务服务
java -jar cloud-business/target/cloud-business-1.0.0.jar
```

### 5. 一键Docker部署
```bash
# 构建所有镜像并启动所有服务
docker-compose up -d
```

## 📊 API文档

启动服务后，可以通过以下地址访问API文档：

- **系统服务**: http://localhost:9001/doc.html
- **认证服务**: http://localhost:9002/doc.html
- **业务服务**: http://localhost:9003/doc.html
- **网关服务**: http://localhost:9000/doc.html

## 🔐 默认账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 超级管理员 |

## 📁 项目结构

```
cloud-model/
├── cloud-common/          # 公共模块
│   ├── src/main/java/com/cloud/common/
│   │   ├── config/        # 公共配置
│   │   ├── entity/        # 基础实体
│   │   ├── result/        # 统一响应
│   │   └── utils/         # 工具类
│   └── pom.xml
├── cloud-gateway/         # 网关服务
│   ├── src/main/java/com/cloud/gateway/
│   ├── src/main/resources/
│   │   └── bootstrap.yml  # 配置文件
│   ├── Dockerfile
│   └── pom.xml
├── cloud-system/          # 系统管理服务
│   ├── src/main/java/com/cloud/system/
│   │   ├── controller/    # 控制器
│   │   ├── entity/        # 实体类
│   │   ├── mapper/        # 数据访问层
│   │   └── service/       # 业务逻辑层
│   ├── src/main/resources/
│   │   ├── mapper/        # SQL映射文件
│   │   └── bootstrap.yml
│   ├── Dockerfile
│   └── pom.xml
├── cloud-auth/            # 认证授权服务
│   ├── src/main/java/com/cloud/auth/
│   ├── src/main/resources/
│   │   └── bootstrap.yml
│   ├── Dockerfile
│   └── pom.xml
├── cloud-business/        # 业务服务
│   ├── src/main/java/com/cloud/business/
│   ├── src/main/resources/
│   │   └── bootstrap.yml
│   ├── Dockerfile
│   └── pom.xml
├── doc/                   # 文档目录
│   └── sql/              # SQL脚本
│       └── init.sql
├── docker-compose.yml     # Docker编排文件
├── pom.xml               # 根项目配置
└── README.md             # 项目说明
```

## 🔄 服务调用流程

```
用户请求 → 网关(9000) → 认证服务(9002) → 系统服务(9001)/业务服务(9003)
```

## 🔧 配置说明

### Nacos配置
- **地址**: http://localhost:8848
- **用户名/密码**: nacos/nacos

### 数据库配置
- **地址**: jdbc:mysql://localhost:3306/
- **用户名/密码**: root/123456

### Redis配置
- **地址**: redis://localhost:6379
- **密码**: 无

## 📋 开发规范

### 分支规范
- `main`: 主分支，生产环境代码
- `develop`: 开发分支，集成测试环境
- `feature/*`: 功能分支，开发新功能
- `hotfix/*`: 热修复分支，修复线上问题

### 代码规范
- 遵循阿里巴巴Java开发手册
- 使用MyBatis-Plus代码生成器
- 统一使用Lombok简化代码
- 统一响应结果使用`Result<T>`

### 提交规范
```
feat: 新功能
fix: 修复bug
docs: 文档更新
style: 代码格式调整
refactor: 代码重构
test: 测试相关
chore: 构建过程或辅助工具的变动
```

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 🆘 支持

如有问题或建议，请提交 [Issue](https://github.com/your-repo/cloud-model/issues) 或联系维护者。

## 🙏 致谢

- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [Nacos](https://nacos.io/)
- [MyBatis-Plus](https://baomidou.com/)
- [Hutool](https://hutool.cn/)

---

**最后更新时间**: 2024年12月