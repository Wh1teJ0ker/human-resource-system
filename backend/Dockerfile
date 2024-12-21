# 使用 Maven 镜像构建 Java 项目
FROM maven:3.8.6-openjdk-11-slim AS build

# 设置工作目录
WORKDIR /app

# 将 pom.xml 文件复制到容器中
COPY backend/pom.xml .

# 下载项目的依赖
RUN mvn dependency:go-offline

# 复制源代码到容器
COPY backend/src /app/src

# 编译并打包项目
RUN mvn clean package -DskipTests

# 使用 OpenJDK 镜像运行 Spring Boot 应用
FROM openjdk:11-jre-slim

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件到容器中
COPY --from=build /app/target/*.jar app.jar

# 运行 Spring Boot 应用
CMD ["java", "-jar", "app.jar"]
