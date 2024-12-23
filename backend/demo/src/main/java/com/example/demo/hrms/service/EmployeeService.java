package com.example.demo.hrms.service;

import com.example.demo.hrms.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 获取员工列表
    public List<Employee> getEmployeeList() {
        String sql = "SELECT e.id, e.name, e.position, d.department_name AS department " +
                "FROM employees e " +
                "JOIN departments d ON e.department_id = d.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Employee(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("position"),
                rs.getString("department")
        ));
    }

    // 添加员工
    public void addEmployee(Employee employee) {
        // 查找部门ID
        String sqlFindDepartmentId = "SELECT id FROM departments WHERE department_name = ?";
        Long departmentId = jdbcTemplate.queryForObject(sqlFindDepartmentId, Long.class, employee.getDepartment());

        if (departmentId == null) {
            throw new RuntimeException("未找到对应的部门");
        }

        // 插入员工数据
        String sql = "INSERT INTO employees (name, position, department_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, employee.getName(), employee.getPosition(), departmentId);
    }

    // 编辑员工
    public void editEmployee(Long id, Employee employee) {
        // 查找部门ID
        String sqlFindDepartmentId = "SELECT id FROM departments WHERE department_name = ?";
        Long departmentId = jdbcTemplate.queryForObject(sqlFindDepartmentId, Long.class, employee.getDepartment());

        if (departmentId == null) {
            throw new RuntimeException("未找到对应的部门");
        }

        // 更新员工信息
        String sql = "UPDATE employees SET name = ?, position = ?, department_id = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, employee.getName(), employee.getPosition(), departmentId, id);

        if (rowsUpdated == 0) {
            throw new RuntimeException("员工未找到，无法更新");
        }
    }

    // 删除员工
    public void deleteEmployee(Long id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        int rowsDeleted = jdbcTemplate.update(sql, id);

        if (rowsDeleted == 0) {
            throw new RuntimeException("员工未找到，无法删除");
        }
    }
}
