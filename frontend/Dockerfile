# 使用 Node.js 官方镜像作为基础镜像
FROM node:16 AS build

# 设置工作目录为 /app
WORKDIR /app

# 将 package.json 和 package-lock.json 复制到容器的工作目录中
COPY package*.json ./

# 安装项目依赖
RUN npm install

# 将整个前端项目代码复制到容器中
COPY . .

# 构建生产版本的 Vue 项目
RUN npm run build

# 使用 nginx 提供构建后的静态文件服务
FROM nginx:alpine

# 将构建后的文件从前一个镜像复制到 nginx 默认的静态文件目录
COPY --from=build /app/dist /usr/share/nginx/html

# 暴露容器的 80 端口
EXPOSE 80

# 启动 nginx
CMD ["nginx", "-g", "daemon off;"]
