# Nacos 配置文件

该目录包含了所有微服务在Nacos配置中心的配置文件。

## 配置文件列表

1. [cloud-gateway.yaml](file:///D:/downloadProject/cloud-model/doc/deploy/nacos/cloud-gateway.yaml) - 网关服务配置
2. [cloud-auth.yaml](file:///D:/downloadProject/cloud-model/doc/deploy/nacos/cloud-auth.yaml) - 认证服务配置
3. [cloud-system.yaml](file:///D:/downloadProject/cloud-model/doc/deploy/nacos/cloud-system.yaml) - 系统管理服务配置
4. [cloud-business.yaml](file:///D:/downloadProject/cloud-model/doc/deploy/nacos/cloud-business.yaml) - 业务服务配置

## 使用说明

1. 启动Nacos服务器
2. 登录Nacos控制台
3. 为每个服务创建对应的配置文件：
   - Data ID: 与文件名一致（如 cloud-gateway.yaml）
   - Group: DEFAULT_GROUP
   - 配置格式: YAML
   - 配置内容: 复制对应文件的内容
4. 启动各微服务，它们将自动从Nacos获取配置

## 配置热更新

修改Nacos中的配置后，相关服务会自动刷新配置，无需重启服务。