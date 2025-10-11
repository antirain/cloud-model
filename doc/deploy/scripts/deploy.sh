#!/bin/bash

# 云模型系统部署脚本
set -e

echo "========================================"
echo "云模型系统部署脚本"
echo "========================================"

# 检查Docker是否安装
if ! command -v docker &> /dev/null; then
    echo "错误: Docker未安装，请先安装Docker"
    exit 1
fi

# 检查Docker Compose是否安装
if ! command -v docker-compose &> /dev/null; then
    echo "错误: Docker Compose未安装，请先安装Docker Compose"
    exit 1
fi

# 创建必要的目录
mkdir -p logs/mysql logs/redis logs/nacos

# 设置文件权限
chmod -R 755 logs/

# 构建所有服务
echo "开始构建所有服务..."
docker-compose build

# 启动基础设施服务
echo "启动基础设施服务 (MySQL, Redis, Nacos)..."
docker-compose up -d mysql redis nacos

# 等待服务启动
echo "等待服务启动..."
sleep 30

# 检查服务状态
echo "检查服务状态..."
docker-compose ps

# 启动应用服务
echo "启动应用服务..."
docker-compose up -d

# 等待所有服务启动完成
sleep 10

echo "========================================"
echo "部署完成！"
echo "========================================"
echo "服务访问地址:"
echo "网关服务: http://localhost:9000"
echo "系统服务: http://localhost:9000/system"
echo "认证服务: http://localhost:9000/auth"
echo "业务服务: http://localhost:9000/business"
echo ""
echo "Nacos控制台: http://localhost:8848/nacos (nacos/nacos)"
echo "MySQL: localhost:3306 (root/123456)"
echo "Redis: localhost:6379"
echo ""
echo "查看日志:"
echo "docker-compose logs -f [service-name]"
echo ""
echo "停止服务:"
echo "docker-compose down"