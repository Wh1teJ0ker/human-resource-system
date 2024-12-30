# 使用 Node.js 官方镜像作为基础镜像
FROM node:16 AS build

# 设置工作目录为 /app
WORKDIR /app

# 仅复制 package.json 和 package-lock.json 文件，这样可以利用 Docker 缓存
COPY package*.json ./

# 安装项目依赖（如果只有生产依赖，可以加上 --production 参数）
RUN npm install

# 将整个前端项目代码复制到容器中
COPY . .

# 构建生产版本的 Vue 项目
RUN npm run build

# 使用 nginx 提供构建后的静态文件服务
FROM nginx:alpine

# 将构建后的文件从前一个镜像复制到 nginx 默认的静态文件目录
COPY --from=build /app/dist /usr/share/nginx/html

# 如果有自定义 nginx 配置文件，可以将其复制到容器中
# COPY nginx.conf /etc/nginx/nginx.conf

# 暴露容器的 80 端口
EXPOSE 80

# 启动 nginx
CMD ["nginx", "-g", "daemon off;"]
