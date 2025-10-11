FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制jar包到容器中
COPY target/cloud-vform-1.0.0.jar app.jar

# 暴露端口
EXPOSE 9006

# 设置JVM参数
ENV JAVA_OPTS="-Xmx512m -Xms256m -Dfile.encoding=UTF-8"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]