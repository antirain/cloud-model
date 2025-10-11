# 部署指南

## 环境要求

### 开发环境
- Java 17 或更高版本
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Nacos 2.2.0+

### 生产环境
- Docker 20.10+
- Docker Compose 2.0+
- Kubernetes 1.20+ (可选)

## 快速开始

### 1. 本地开发部署

#### 1.1 启动基础设施
```bash
# 使用Docker Compose启动基础设施
docker-compose up -d mysql redis nacos

# 等待服务启动
sleep 30
```

#### 1.2 初始化数据库
```bash
# 执行数据库初始化脚本
mysql -h localhost -P 3306 -u root -p < doc/sql/init.sql
```

#### 1.3 启动应用服务
```bash
# 分别启动各个服务
cd cloud-gateway && mvn spring-boot:run
cd cloud-system && mvn spring-boot:run
cd cloud-auth && mvn spring-boot:run
cd cloud-business && mvn spring-boot:run
```

### 2. Docker部署

#### 2.1 一键部署
```bash
# 执行部署脚本
./doc/deploy/scripts/deploy.sh
```

#### 2.2 手动部署
```bash
# 构建所有服务
./doc/deploy/scripts/build.sh

# 启动所有服务
docker-compose up -d
```

### 3. Kubernetes部署

#### 3.1 创建命名空间
```bash
kubectl apply -f doc/deploy/k8s/namespace.yaml
```

#### 3.2 部署基础设施
```bash
# 部署MySQL
kubectl apply -f doc/deploy/k8s/mysql-deployment.yaml

# 部署Nacos
kubectl apply -f doc/deploy/k8s/nacos-deployment.yaml
```

#### 3.3 部署应用服务
```bash
# 构建镜像并推送
./doc/deploy/scripts/build.sh

# 部署各个服务 (需要创建对应的K8s配置文件)
kubectl apply -f doc/deploy/k8s/gateway-deployment.yaml
kubectl apply -f doc/deploy/k8s/system-deployment.yaml
kubectl apply -f doc/deploy/k8s/auth-deployment.yaml
kubectl apply -f doc/deploy/k8s/business-deployment.yaml
```

## 服务访问

| 服务 | 地址 | 描述 |
|------|------|------|
| 网关服务 | http://localhost:9000 | 统一入口 |
| 系统服务 | http://localhost:9000/system | 用户管理 |
| 认证服务 | http://localhost:9000/auth | 登录认证 |
| 业务服务 | http://localhost:9000/business | 业务功能 |
| Nacos控制台 | http://localhost:8848/nacos | 配置中心 |
| API文档 | http://localhost:9000/doc.html | Swagger文档 |

## 配置说明

### 环境变量
复制 `.env.example` 为 `.env` 并修改配置：
```bash
cp .env.example .env
# 编辑 .env 文件
```

### 数据库配置
- 主机: localhost:3306
- 用户名: root
- 密码: 123456
- 数据库: cloud_system, cloud_auth, cloud_business

### Redis配置
- 主机: localhost:6379
- 密码: 无

## 监控和日志

### 查看日志
```bash
# Docker Compose日志
docker-compose logs -f [service-name]

# Kubernetes日志
kubectl logs -f [pod-name] -n cloud-model
```

### 健康检查
```bash
# 检查服务状态
curl http://localhost:9000/actuator/health
```

## 故障排查

### 常见问题

1. **端口占用错误**
   - 检查端口是否被占用：`netstat -tulnp | grep 9000`
   - 修改端口配置

2. **数据库连接失败**
   - 检查MySQL是否启动：`docker-compose ps`
   - 检查数据库配置是否正确
   - 检查防火墙设置

3. **服务注册失败**
   - 检查Nacos是否启动
   - 检查网络连接
   - 检查服务配置

4. **认证失败**
   - 检查JWT密钥配置
   - 检查用户权限
   - 检查Token有效期

### 调试工具

1. **Postman**: 使用 `doc/api/postman-collection.json`
2. **Swagger**: 访问 http://localhost:9000/doc.html
3. **Nacos**: 访问 http://localhost:8848/nacos

## 扩展部署

### 集群部署
- 使用Kubernetes进行容器编排
- 配置负载均衡器
- 设置服务发现和注册

### 高可用部署
- 配置MySQL主从复制
- 配置Redis集群
- 配置Nacos集群

### 监控告警
- 集成Prometheus + Grafana
- 配置ELK日志收集
- 设置告警规则