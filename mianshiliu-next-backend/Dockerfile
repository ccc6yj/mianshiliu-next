# Docker 镜像构建
# 多阶段构建：先构建 JAR，再打包成精简镜像

FROM maven:3.8.1-jdk-8-slim as builder

WORKDIR /app

# 复制 pom.xml 和本地 lib 依赖
COPY pom.xml .
COPY lib ./lib

# 先下载依赖（利用 Docker 缓存）
RUN mvn dependency:go-offline -B

# 复制源代码并构建
COPY src ./src
RUN mvn package -DskipTests

# 运行阶段 - 使用更小的基础镜像
FROM openjdk:8-jre-slim

WORKDIR /app

# 从构建阶段复制 JAR 文件
COPY --from=builder /app/target/mianshiliu-next-backend-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口
EXPOSE 8101

# JVM 参数
ENV JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.profiles.active=prod"]