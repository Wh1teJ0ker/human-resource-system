-- 选择信息模式数据库
USE information_schema;

-- 禁用外键检查
SET foreign_key_checks = 0;

-- 删除 hr_system 数据库（如果存在）
DROP DATABASE IF EXISTS hr_system;

-- 创建新的 hr_system 数据库，并指定字符集为 utf8mb4
CREATE DATABASE hr_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 切换到新创建的数据库
USE hr_system;

-- 创建 departments 表（如果表不存在）
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建 accounts 表，并确保 id 从 0 开始
CREATE TABLE accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 设置自增值从 0 开始
ALTER TABLE accounts AUTO_INCREMENT = 0;

-- 创建 employees 表（如果表不存在）
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    department_id BIGINT DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL
);

-- 创建 attendances 表（如果表不存在）
CREATE TABLE IF NOT EXISTS attendances (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT DEFAULT NULL,
    attendance_date DATE NOT NULL,
    attendance_status ENUM('present', 'absent', 'late') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE SET NULL
);

-- 创建 salaries 表（如果表不存在）
CREATE TABLE IF NOT EXISTS salaries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT DEFAULT NULL,
    base_salary DECIMAL(10, 2) NOT NULL,
    bonus DECIMAL(10, 2) NOT NULL,
    benefits DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE SET NULL
);

-- 创建 leave_requests 表（如果表不存在）
CREATE TABLE IF NOT EXISTS leave_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT DEFAULT NULL,
    leave_date DATE NOT NULL,
    leave_type ENUM('sick', 'annual', 'personal') NOT NULL,
    leave_status ENUM('approved', 'pending', 'rejected') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE SET NULL
);

-- 创建视图：查看所有员工信息，包括部门名称和考勤情况
CREATE VIEW employee_info AS
SELECT e.id AS employee_id, e.name, e.position, d.department_name, a.attendance_status
FROM employees e
JOIN departments d ON e.department_id = d.id
LEFT JOIN attendances a ON e.id = a.employee_id;

-- 创建存储过程：更新员工薪资
DELIMITER $$

CREATE PROCEDURE update_salary(IN IN_employee_id BIGINT, IN new_salary DECIMAL(10, 2), IN new_bonus DECIMAL(10, 2), IN new_benefits DECIMAL(10, 2))
BEGIN
    UPDATE salaries 
    SET base_salary = new_salary, bonus = new_bonus, benefits = new_benefits, updated_at = CURRENT_TIMESTAMP
    WHERE employee_id = IN_employee_id;
END$$

DELIMITER ;

-- 创建触发器：员工删除后自动更新相关表中的 employee_id 为 NULL
DELIMITER $$

CREATE TRIGGER update_employee_id_after_delete
AFTER DELETE ON employees
FOR EACH ROW
BEGIN
    -- 更新考勤表中的 employee_id
    UPDATE attendances
    SET employee_id = NULL
    WHERE employee_id = OLD.id;

    -- 更新薪资表中的 employee_id
    UPDATE salaries
    SET employee_id = NULL
    WHERE employee_id = OLD.id;

    -- 更新请假请求表中的 employee_id
    UPDATE leave_requests
    SET employee_id = NULL
    WHERE employee_id = OLD.id;
END$$

DELIMITER ;

-- 存储过程：删除员工及其相关记录
DELIMITER $$

CREATE PROCEDURE delete_employee_and_related_data(IN IN_employee_id BIGINT)
BEGIN
    -- 删除员工相关数据
    DELETE FROM leave_requests WHERE employee_id = IN_employee_id;
    DELETE FROM attendances WHERE employee_id = IN_employee_id;
    DELETE FROM salaries WHERE employee_id = IN_employee_id;
    DELETE FROM employees WHERE id = IN_employee_id;
END$$

DELIMITER ;

-- 触发器：员工删除后自动调用存储过程
DELIMITER $$

CREATE TRIGGER after_employee_delete
AFTER DELETE ON employees
FOR EACH ROW
BEGIN
    CALL delete_employee_and_related_data(OLD.id);
END$$

DELIMITER ;

-- 插入数据到 accounts 表
INSERT INTO accounts (username, password, role)
VALUES
('admin', 'admin', 'ADMIN'),
('user1', 'password1', 'USER'),
('user2', 'password2', 'USER'),
('user3', 'password3', 'USER');

-- 插入数据到 departments 表
INSERT INTO departments (department_name) VALUES
('技术部'),
('产品部'),
('设计部'),
('市场部'),
('运营部'),
('财务部'),
('人力资源部'),
('法律部'),
('采购部'),
('物流部');

-- 插入数据到 employees 表
INSERT INTO employees (name, position, department_id) VALUES
('张三', '软件工程师', 1),
('李四', '产品经理', 2),
('王五', 'UI设计师', 3),
('赵六', '市场专员', 4),
('孙七', '运营经理', 5),
('周八', '财务主管', 6),
('吴九', '人力资源经理', 7),
('郑十', '法律顾问', 8),
('钱十一', '采购专员', 9),
('刘十二', '物流经理', 10);

-- 插入数据到 attendances 表
INSERT INTO attendances (employee_id, attendance_date, attendance_status) VALUES
(1, '2024-12-01', 'present'),
(2, '2024-12-01', 'absent'),
(3, '2024-12-01', 'late'),
(4, '2024-12-01', 'present'),
(5, '2024-12-01', 'absent'),
(6, '2024-12-01', 'late'),
(7, '2024-12-01', 'present'),
(8, '2024-12-01', 'absent'),
(9, '2024-12-01', 'late'),
(10, '2024-12-01', 'present'),
(1, '2024-12-02', 'absent'),
(2, '2024-12-02', 'present'),
(3, '2024-12-02', 'late'),
(4, '2024-12-02', 'present'),
(5, '2024-12-02', 'absent');

-- 插入数据到 salaries 表
INSERT INTO salaries (employee_id, base_salary, bonus, benefits) VALUES
(1, 5000, 1000, 500),
(2, 4500, 800, 400),
(3, 6000, 1200, 600),
(4, 5500, 1000, 550),
(5, 4800, 900, 450),
(6, 7000, 1500, 700),
(7, 6500, 1300, 650),
(8, 6200, 1200, 600),
(9, 5300, 950, 480),
(10, 6900, 1400, 680),
(1, 5100, 1050, 510),
(2, 4600, 850, 420),
(3, 6100, 1250, 620),
(4, 5600, 1050, 560),
(5, 4900, 950, 460);

-- 插入数据到 leave_requests 表
INSERT INTO leave_requests (employee_id, leave_date, leave_type, leave_status) VALUES
(1, '2024-12-05', 'sick', 'approved'),
(2, '2024-12-06', 'annual', 'pending'),
(3, '2024-12-07', 'personal', 'rejected'),
(4, '2024-12-08', 'sick', 'approved'),
(5, '2024-12-09', 'annual', 'pending'),
(6, '2024-12-10', 'personal', 'approved'),
(7, '2024-12-11', 'sick', 'approved'),
(8, '2024-12-12', 'annual', 'rejected'),
(9, '2024-12-13', 'personal', 'approved'),
(10, '2024-12-14', 'sick', 'pending'),
(1, '2024-12-15', 'annual', 'approved'),
(2, '2024-12-16', 'personal', 'rejected'),
(3, '2024-12-17', 'sick', 'approved'),
(4, '2024-12-18', 'annual', 'pending'),
(5, '2024-12-19', 'personal', 'approved');


