## API接口文档

### AccountSettings

#### 修改管理员账户信息

接口：`POST /api/account/updatePassword`

请求参数：

| 参数     | 类型   | 必填 | 描述       |
| ---------- | -------- | ------ | ------------ |
| username | string | 是   | 新的用户名 |
| password | string | 是   | 新的密码   |

请求示例：

```json
{
  "username": "new_admin",
  "password": "new_password123"
}
```

响应：

* **成功**（200 OK）：

  * 状态码：`200`
  * 响应体：

    ```json
    {
      "message": "管理员账户修改成功"
    }
    ```
* **失败**（400 Bad Request）：

  * 状态码：`400`
  * 响应体：

    ```json
    {
      "message": "用户名或密码不能为空"
    }
    ```
* **服务器错误**（500 Internal Server Error）：

  * 状态码：`500`
  * 响应体：

    ```json
    {
      "message": "服务器错误，请稍后重试"
    }
    ```

### Dashboard

#### 控制面板数据接口

接口：`GET /api/dashboard`

请求参数：

无

请求示例：

```json
// 无请求参数
```

响应：

* **成功**（200 OK）：

  * 状态码：`200`
  * 响应体：

    ```json
    {
      "employeeCount": 120,     // 当前员工数量
      "departmentCount": 15,    // 部门数量
      "attendanceRate": 98.5,   // 出勤率（百分比）
      "leaveRequests": 8       // 请假统计
    }
    ```
* **失败**（500 Internal Server Error）：

  * 状态码：`500`
  * 响应体：

    ```json
    {
      "message": "服务器错误，请稍后重试"
    }
    ```

### EmployeeManagement

#### 获取员工列表

* **接口：**  `GET /api/employees`
* **请求参数：**  无
* **响应：**

  * **成功**（200 OK）：

    * 状态码：`200`
    * 响应体：

    ```json
    [
      {
        "id": 1,
        "name": "张三",
        "position": "软件工程师",
        "department": "技术部"
      },
      {
        "id": 2,
        "name": "李四",
        "position": "产品经理",
        "department": "产品部"
      }
    ]
    ```
  * **失败**（500 Internal Server Error）：

    * 状态码：`500`
    * 响应体：

    ```json
    {
      "message": "获取员工列表失败"
    }
    ```

#### 添加员工

* **接口：**  `POST /api/employees`
* | **请求参数：**           |        |    |          |
  | 参数 | 类型 | 必填 | 描述 |
  | ------------ | -------- | ---- | ---------- |
  | name       | string | 是 | 员工姓名 |
  | position   | string | 是 | 员工职位 |
  | department | string | 是 | 所在部门 |
* **请求示例：**

  ```json
  {
    "name": "王五",
    "position": "UI设计师",
    "department": "设计部"
  }
  ```
* **响应：**

  * **成功**（201 Created）：

    * 状态码：`201`
    * 响应体：

    ```json
    {
      "message": "员工添加成功"
    }
    ```
  * **失败**（400 Bad Request）：

    * 状态码：`400`
    * 响应体：

    ```json
    {
      "message": "添加员工失败"
    }
    ```

#### 编辑员工

* **接口：**  `PUT /api/employees/{id}`
* | **请求参数：**           |        |    |          |
  | 参数 | 类型 | 必填 | 描述 |
  | ------------ | -------- | ---- | ---------- |
  | name       | string | 是 | 员工姓名 |
  | position   | string | 是 | 员工职位 |
  | department | string | 是 | 所在部门 |
* **请求示例：**

  ```json
  {
    "name": "张三",
    "position": "高级软件工程师",
    "department": "技术部"
  }
  ```
* **响应：**

  * **成功**（200 OK）：

    * 状态码：`200`
    * 响应体：

    ```json
    {
      "message": "员工信息更新成功"
    }
    ```
  * **失败**（400 Bad Request）：

    * 状态码：`400`
    * 响应体：

    ```json
    {
      "message": "编辑员工失败"
    }
    ```

#### 4. 删除员工

* **接口：**  `DELETE /api/employees/{id}`
* **请求参数：**  无
* **响应：**

  * **成功**（200 OK）：

    * 状态码：`200`
    * 响应体：

    ```json
    {
      "message": "员工删除成功"
    }
    ```
  * **失败**（500 Internal Server Error）：

    * 状态码：`500`
    * 响应体：

    ```json
    {
      "message": "删除员工失败"
    }
    ```

### 部门管理接口

#### 1. 获取部门列表

* **接口：**  `GET /api/departments`
* **请求参数：**  无
* **响应：**

  * **成功**（200 OK）：

    * 状态码：`200`
    * 响应体：

    ```json
    [
      {
        "id": 1,
        "departmentName": "技术部"
      },
      {
        "id": 2,
        "departmentName": "产品部"
      }
    ]
    ```
  * **失败**（500 Internal Server Error）：

    * 状态码：`500`
    * 响应体：

    ```json
    {
      "message": "获取部门列表失败"
    }
    ```

#### 2. 添加部门

* **接口：**  `POST /api/departments`
* | **请求参数：**               |        |    |          |
  | 参数 | 类型 | 必填 | 描述 |
  | ---------------- | -------- | ---- | ---------- |
  | departmentName | string | 是 | 部门名称 |
* **请求示例：**

  ```json
  {
    "departmentName": "市场部"
  }
  ```
* **响应：**

  * **成功**（201 Created）：

    * 状态码：`201`
    * 响应体：

    ```json
    {
      "message": "部门添加成功"
    }
    ```
  * **失败**（400 Bad Request）：

    * 状态码：`400`
    * 响应体：

    ```json
    {
      "message": "添加部门失败"
    }
    ```

#### 3. 编辑部门

* **接口：**  `PUT /api/departments/{id}`
* | **请求参数：**               |        |    |          |
  | 参数 | 类型 | 必填 | 描述 |
  | ---------------- | -------- | ---- | ---------- |
  | departmentName | string | 是 | 部门名称 |
* **请求示例：**

  ```json
  {
    "departmentName": "运营部"
  }
  ```
* **响应：**

  * **成功**（200 OK）：

    * 状态码：`200`
    * 响应体：

    ```json
    {
      "message": "部门编辑成功"
    }
    ```
  * **失败**（400 Bad Request）：

    * 状态码：`400`
    * 响应体：

    ```json
    {
      "message": "编辑部门失败"
    }
    ```

#### 4. 删除部门

* **接口：**  `DELETE /api/departments/{id}`
* **请求参数：**  无
* **响应：**

  * **成功**（200 OK）：

    * 状态码：`200`
    * 响应体：

    ```json
    {
      "message": "部门删除成功"
    }
    ```
  * **失败**（500 Internal Server Error）：

    * 状态码：`500`
    * 响应体：

    ```json
    {
      "message": "删除部门失败"
    }
    ```

### 考勤管理接口

### LeaveManagement

#### 1. 获取考勤记录列表

* **接口：**  `GET /api/attendance`
* **请求参数：**  无
* **响应：**

  * **成功**（200 OK）：

    * 状态码：`200`
    * 响应体：

    ```json
    [
      {
        "id": 1,
        "employeeName": "张三",
        "attendanceDate": "2024-12-01",
        "attendanceStatus": "present"
      },
      {
        "id": 2,
        "employeeName": "李四",
        "attendanceDate": "2024-12-01",
        "attendanceStatus": "absent"
      }
    ]
    ```
  * **失败**（500 Internal Server Error）：

    * 状态码：`500`
    * 响应体：

    ```json
    {
      "message": "获取考勤记录失败"
    }
    ```

#### 2. 添加考勤记录

* **接口：**  `POST /api/attendance`
* | **请求参数：**                 |        |    |                |
  | 参数 | 类型 | 必填 | 描述 |
  | ------------------ | -------- | ---- | ---------------- |
  | employeeName     | string | 是 | 员工姓名       |
  | attendanceDate   | string | 是 | 考勤日期       |
  | attendanceStatus | string | 是 | 出勤状态（`present`,`absent`,`late`） |
* **请求示例：**

  ```json
  {
    "employeeName": "王五",
    "attendanceDate": "2024-12-02",
    "attendanceStatus": "late"
  }
  ```
* **响应：**

  * **成功**（201 Created）：

    * 状态码：`201`
    * 响应体：

    ```json
    {
      "message": "考勤记录添加成功"
    }
    ```
  * **失败**（400 Bad Request）：

    * 状态码：`400`
    * 响应体：

    ```json
    {
      "message": "添加考勤记录失败"
    }
    ```

#### 3. 编辑考勤记录

* **接口：**  `PUT /api/attendance/{id}`
* | **请求参数：**                 |        |    |                |
  | 参数 | 类型 | 必填 | 描述 |
  | ------------------ | -------- | ---- | ---------------- |
  | employeeName     | string | 是 | 员工姓名       |
  | attendanceDate   | string | 是 | 考勤日期       |
  | attendanceStatus | string | 是 | 出勤状态（`present`,`absent`,`late`） |
* **请求示例：**

  ```json
  {
    "employeeName": "张三",
    "attendanceDate": "2024-12-01",
    "attendanceStatus": "present"
  }
  ```
* **响应：**

  * **成功**（200 OK）：

    * 状态码：`200`
    * 响应体：

    ```json
    {
      "message": "考勤记录编辑成功"
    }
    ```
  * **失败**（400 Bad Request）：

    * 状态码：`400`
    * 响应体：

    ```json
    {
      "message": "编辑考勤记录失败"
    }
    ```

#### 4. 删除考勤记录

* **接口：**  `DELETE /api/attendance/{id}`
* **请求参数：**  无
* **响应：**

  * **成功**（200 OK）：

    * 状态码：`200`
    * 响应体：

    ```json
    {
      "message": "考勤记录删除成功"
    }
    ```
  * **失败**（500 Internal Server Error）：

    * 状态码：`500`
    * 响应体：

    ```json
    {
      "message": "删除考勤记录失败"
    }
    ```

### 薪资管理接口

#### 1. 获取薪资记录列表

* **接口：**  `GET /api/salaries`
* **请求参数：**  无
* **响应：**

  * **成功**（200 OK）：

    * 状态码：`200`
    * 响应体：

    ```json
    [
      {
        "id": 1,
        "employeeName": "张三",
        "baseSalary": 5000,
        "bonus": 1000,
        "benefits": 500
      },
      {
        "id": 2,
        "employeeName": "李四",
        "baseSalary": 4500,
        "bonus": 800,
        "benefits": 400
      }
    ]
    ```
  * **失败**（500 Internal Server Error）：

    * 状态码：`500`
    * 响应体：

    ```json
    {
      "message": "获取薪资记录失败"
    }
    ```

#### 2. 添加薪资记录

* **接口：**  `POST /api/salaries`
* | **请求参数：**             |        |    |          |
  | 参数 | 类型 | 必填 | 描述 |
  | -------------- | -------- | ---- | ---------- |
  | employeeName | string | 是 | 员工姓名 |
  | baseSalary   | number | 是 | 基本薪资 |
  | bonus        | number | 是 | 奖金     |
  | benefits     | number | 是 | 其他福利 |
* **请求示例：**

  ```json
  {
    "employeeName": "王五",
    "baseSalary": 6000,
    "bonus": 1200,
    "benefits": 600
  }
  ```
* **响应：**

  * **成功**（201 Created）：

    * 状态码：`201`
    * 响应体：

    ```json
    {
      "message": "薪资记录添加成功"
    }
    ```
  * **失败**（400 Bad Request）：

    * 状态码：`400`
    * 响应体：

    ```json
    {
      "message": "添加薪资记录失败"
    }
    ```

#### 3. 编辑薪资记录

* **接口：**  `PUT /api/salaries/{id}`
* | **请求参数：**             |        |    |          |
  | 参数 | 类型 | 必填 | 描述 |
  | -------------- | -------- | ---- | ---------- |
  | employeeName | string | 是 | 员工姓名 |
  | baseSalary   | number | 是 | 基本薪资 |
  | bonus        | number | 是 | 奖金     |
  | benefits     | number | 是 | 其他福利 |
* **请求示例：**

  ```json
  {
    "employeeName": "张三",
    "baseSalary": 5000,
    "bonus": 1100,
    "benefits": 550
  }
  ```
* **响应：**

  * **成功**（200 OK）：

    * 状态码：`200`
    * 响应体：

    ```json
    {
      "message": "薪资记录编辑成功"
    }
    ```
  * **失败**（400 Bad Request）：

    * 状态码：`400`
    * 响应体：

    ```json
    {
      "message": "编辑薪资记录失败"
    }
    ```

#### 4. 删除薪资记录

* **接口：**  `DELETE /api/salaries/{id}`
* **请求参数：**  无
* **响应：**

  * **成功**（200 OK）：

    * 状态码：`200`
    * 响应体：

    ```json
    {
      "message": "薪资记录删除成功"
    }
    ```
  * **失败**（500 Internal Server Error）：

    * 状态码：`500`
    * 响应体：

    ```json
    {
      "message": "删除薪资记录失败"
    }
    ```