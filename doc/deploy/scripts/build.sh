#!/bin/bash

# 云模型系统构建脚本
set -e

echo "========================================"
echo "云模型系统构建脚本"
echo "========================================"

# 检查Maven是否安装
if ! command -v mvn &> /dev/null; then
    echo "错误: Maven未安装，请先安装Maven"
    exit 1
fi

# 检查Java版本
if ! command -v java &> /dev/null; then
    echo "错误: Java未安装，请先安装Java 17+"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "错误: 需要Java 17或更高版本，当前版本: $JAVA_VERSION"
    exit 1
fi

# 清理所有模块
echo "清理所有模块..."
mvn clean

# 编译所有模块
echo "编译所有模块..."
mvn compile

# 打包所有模块
echo "打包所有模块..."
mvn package -DskipTests

# 创建构建目录
mkdir -p build

# 复制所有jar包到构建目录
echo "复制构建产物..."
cp cloud-gateway/target/cloud-gateway-*.jar build/cloud-gateway.jar
cp cloud-system/target/cloud-system-*.jar build/cloud-system.jar
cp cloud-auth/target/cloud-auth-*.jar build/cloud-auth.jar
cp cloud-business/target/cloud-business-*.jar build/cloud-business.jar

# 复制Dockerfile
cp cloud-gateway/Dockerfile build/cloud-gateway.Dockerfile
cp cloud-system/Dockerfile build/cloud-system.Dockerfile
cp cloud-auth/Dockerfile build/cloud-auth.Dockerfile
cp cloud-business/Dockerfile build/cloud-business.Dockerfile

echo "========================================"
echo "构建完成！"
echo "========================================"
echo "构建产物目录: build/"
echo ""
echo "构建文件:"
ls -la build/