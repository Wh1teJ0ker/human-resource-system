# 使用 Maven 和 OpenJDK 17 的官方镜像作为构建阶段的基础镜像
FROM maven:3.9-amazoncorretto-17-alpine AS build

# 设置工作目录
WORKDIR /app

# 复制 Maven 配置文件（pom.xml）和其他必要文件
COPY ./demo/pom.xml ./

# 下载依赖（仅用于构建阶段，避免重复下载）
RUN mvn dependency:go-offline

# 将项目的源代码复制到容器中
COPY ./demo ./src

# 构建 Spring Boot 项目，生成 jar 文件
RUN mvn clean package -DskipTests

# 使用一个较小的基础镜像，只包含 JRE 环境
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制构建阶段生成的 jar 文件到容器中
COPY --from=build /app/target/*.jar /app/app.jar

# 暴露容器的 8080 端口
EXPOSE 8080

# 启动 Spring Boot 应用
CMD ["java", "-jar", "/app/app.jar"]
