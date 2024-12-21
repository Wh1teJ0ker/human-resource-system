#!/bin/bash

# 定义根目录
ROOT_DIR="frontend"

# 创建目录结构
mkdir -p $ROOT_DIR/public
mkdir -p $ROOT_DIR/src/assets
mkdir -p $ROOT_DIR/src/components
mkdir -p $ROOT_DIR/src/views
mkdir -p $ROOT_DIR/src/router
mkdir -p $ROOT_DIR/src/store
mkdir -p $ROOT_DIR/src/styles

# 创建文件
touch $ROOT_DIR/public/index.html
touch $ROOT_DIR/public/favicon.ico
touch $ROOT_DIR/src/assets/logo.png
touch $ROOT_DIR/src/components/Header.vue
touch $ROOT_DIR/src/components/Sidebar.vue
touch $ROOT_DIR/src/views/Dashboard.vue
touch $ROOT_DIR/src/views/EmployeeManagement.vue
touch $ROOT_DIR/src/views/DepartmentManagement.vue
touch $ROOT_DIR/src/views/AttendanceManagement.vue
touch $ROOT_DIR/src/views/LeaveManagement.vue
touch $ROOT_DIR/src/views/SalaryManagement.vue
touch $ROOT_DIR/src/router/index.js
touch $ROOT_DIR/src/styles/global.css
touch $ROOT_DIR/src/App.vue
touch $ROOT_DIR/src/main.js
touch $ROOT_DIR/package.json

# 提示用户
echo "前端目录结构和文件已创建完成！"
