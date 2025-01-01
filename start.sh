#!/bin/bash

# 检查并杀死占用 8080 端口的进程
echo "Checking if port 8080 is in use..."
if lsof -i :8080 > /dev/null; then
  echo "Port 8080 is in use. Killing the process..."
  kill -9 $(lsof -t -i :8080)
else
  echo "Port 8080 is not in use."
fi

# 检查并杀死占用 8081 端口的进程
echo "Checking if port 8081 is in use..."
if lsof -i :8081 > /dev/null; then
  echo "Port 8081 is in use. Killing the process..."
  kill -9 $(lsof -t -i :8081)
else
  echo "Port 8081 is not in use."
fi

# 启动 Spring Boot 应用
echo "Starting Spring Boot application..."
cd /home/JavaWeb/human-resource-system/backend/demo
mvn spring-boot:run &

# 等待 Spring Boot 应用启动并返回有效数据
echo "Waiting for Spring Boot application to start..."
until curl -s http://localhost:8080/api/dashboard | grep -q "employeeCount"; do
  echo "Waiting for valid data from /api/dashboard..."
  sleep 5
done

echo "Spring Boot application has started successfully with valid data!"

# 启动 Node.js 应用
echo "Starting Node.js application..."
cd /home/JavaWeb/human-resource-system/frontend
PORT=8081 npm run serve &
