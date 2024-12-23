package com.example.demo.hrms.service;

import com.example.demo.hrms.model.Department;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final JdbcTemplate jdbcTemplate;

    public DepartmentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 获取部门列表
    public List<Department> getDepartmentList() {
        String sql = "SELECT id, department_name FROM departments";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Department(
                rs.getLong("id"),
                rs.getString("department_name")
        ));
    }

    // 添加部门
    public void addDepartment(Department department) {
        String sql = "INSERT INTO departments (department_name, created_at, updated_at) VALUES (?, NOW(), NOW())";
        jdbcTemplate.update(sql, department.getDepartmentName());
    }

    // 编辑部门
    public void editDepartment(Long id, Department department) {
        String sql = "UPDATE departments SET department_name = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, department.getDepartmentName(), id);
        if (rowsUpdated == 0) {
            throw new RuntimeException("部门未找到，无法更新");
        }
    }

    // 删除部门
    public void deleteDepartment(Long id) {
        String sql = "DELETE FROM departments WHERE id = ?";
        int rowsDeleted = jdbcTemplate.update(sql, id);
        if (rowsDeleted == 0) {
            throw new RuntimeException("部门未找到，无法删除");
        }
    }
}
