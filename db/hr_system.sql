-- 选择 hr_system 数据库
USE hr_system;

-- 创建 departments 表（如果表不存在）
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建 accounts 表（如果表不存在）
CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') DEFAULT 'USER', -- 权限信息列
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建 employees 表（如果表不存在）
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    department_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE
);

-- 创建 attendances 表（如果表不存在）
CREATE TABLE IF NOT EXISTS attendances (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    attendance_status ENUM('present', 'absent', 'late') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

-- 创建 salaries 表（如果表不存在）
CREATE TABLE IF NOT EXISTS salaries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    base_salary DECIMAL(10, 2) NOT NULL,
    bonus DECIMAL(10, 2) NOT NULL,
    benefits DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

-- 创建 leave_requests 表（如果表不存在）
CREATE TABLE IF NOT EXISTS leave_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    leave_date DATE NOT NULL,
    leave_type ENUM('sick', 'annual', 'personal') NOT NULL,
    leave_status ENUM('approved', 'pending', 'rejected') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

-- 插入数据到 departments 表
INSERT INTO departments (department_name) VALUES
('技术部'),
('产品部'),
('设计部'),
('市场部'),
('运营部');

-- 插入数据到 employees 表
INSERT INTO employees (name, position, department_id) VALUES
('张三', '软件工程师', 1),
('李四', '产品经理', 2),
('王五', 'UI设计师', 3),
('赵六', '市场专员', 4),
('孙七', '运营经理', 5);

-- 插入数据到 attendances 表
INSERT INTO attendances (employee_id, attendance_date, attendance_status) VALUES
(1, '2024-12-01', 'present'),
(2, '2024-12-01', 'absent'),
(3, '2024-12-01', 'late');

-- 插入数据到 salaries 表
INSERT INTO salaries (employee_id, base_salary, bonus, benefits) VALUES
(1, 5000, 1000, 500),
(2, 4500, 800, 400),
(3, 6000, 1200, 600);

-- 插入数据到 leave_requests 表
INSERT INTO leave_requests (employee_id, leave_date, leave_type, leave_status) VALUES
(1, '2024-12-05', 'sick', 'approved'),
(2, '2024-12-06', 'annual', 'pending'),
(3, '2024-12-07', 'personal', 'rejected');
