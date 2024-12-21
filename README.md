# Human Resource System

## 项目简介
学校课程设计练手的小玩意，难度不高，仅供参考

SpringBoot+Vue+Nginx

---

## 前端构建

### 1. 进入前端项目目录

首先，进入 `frontend` 目录：

```shell
cd frontend
```

### 2. 构建前端镜像

在 `frontend` 目录下使用 Docker 构建前端镜像。此命令会根据 Dockerfile 构建 Vue.js 应用并准备静态文件。

```shell
docker build -t vue-frontend .
```

### 3. 启动前端容器

在构建完成后，运行以下命令启动一个 Docker 容器，并将容器的 80 端口映射到本地的 8080 端口：

```shell
docker run -d -p 8080:80 vue-frontend
```

此时，您可以通过访问 `http://localhost:8080` 查看前端页面。

---

## 后端构建

### 1. 进入后端项目目录

进入 `backend` 目录：

```shell
cd backend
```

### 2. 构建后端镜像

在 `backend` 目录下使用 Docker 构建后端镜像。确保您有一个包含 `Dockerfile` 的后端项目目录，通常 `Dockerfile` 会包含构建 Spring Boot 应用的指令。

```shell
docker build -t spring-backend .
```

### 3. 启动后端容器

在构建完成后，运行以下命令启动一个 Docker 容器，并将容器的 8080 端口映射到本地的 8081 端口（确保与前端的端口不冲突）：

```shell
docker run -d -p 8081:8080 spring-backend
```

此时，您可以通过访问 `http://localhost:8081` 访问后端接口。

---

## 使用 Docker Compose 启动前后端

为了方便管理前后端容器，我们可以使用 `docker-compose` 来一起启动它们。

### 1. 创建 `docker-compose.yml` 文件

在 `human-resource-system` 项目根目录下创建 `docker-compose.yml` 文件，内容如下：

```yaml
version: '3.8'

services:
  frontend:
    build:
      context: ./frontend
      dockerfile: Dockerfile
    ports:
      - "8080:80"
    networks:
      - hr-network

  backend:
    build:
      context: ./backend
      dockerfile: Dockerfile
    ports:
      - "8081:8080"
    networks:
      - hr-network

networks:
  hr-network:
    driver: bridge
```

### 2. 使用 Docker Compose 启动

确保 `docker-compose.yml` 文件在项目根目录下，然后运行以下命令来构建和启动前后端容器：

```shell
cd human-resource-system
docker-compose up -d
```

- `docker-compose up -d` 命令会在后台启动前后端服务。
- 前端应用将运行在 `http://localhost:8080`。
- 后端应用将运行在 `http://localhost:8081`。

### 3. 停止和删除容器

若要停止并删除所有容器，可以运行以下命令：

```shell
docker-compose down
```



