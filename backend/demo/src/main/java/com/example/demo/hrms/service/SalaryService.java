package com.example.demo.hrms.service;

import com.example.demo.hrms.model.Salary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class SalaryService {

    private final JdbcTemplate jdbcTemplate;

    public SalaryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 获取所有薪资记录
    public List<Salary> getAllSalaries() {
        String sql = "SELECT * FROM salaries";
        return jdbcTemplate.query(sql, new SalaryRowMapper());
    }

    // 添加薪资记录
    public void addSalary(Salary salary) {
        String sql = "INSERT INTO salaries (employee_id, base_salary, bonus, benefits) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, salary.getEmployeeId(), salary.getBaseSalary(), salary.getBonus(), salary.getBenefits());
    }

    // 更新薪资记录
    public void updateSalary(Long id, Salary updatedSalary) {
        String sql = "UPDATE salaries SET employee_id = ?, base_salary = ?, bonus = ?, benefits = ? WHERE id = ?";
        jdbcTemplate.update(sql, updatedSalary.getEmployeeId(), updatedSalary.getBaseSalary(), updatedSalary.getBonus(), updatedSalary.getBenefits(), id);
    }

    // 删除薪资记录
    public void deleteSalary(Long id) {
        String sql = "DELETE FROM salaries WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // RowMapper 实现
    private static class SalaryRowMapper implements RowMapper<Salary> {
        @Override
        public Salary mapRow(ResultSet rs, int rowNum) throws SQLException {
            Salary salary = new Salary();
            salary.setId(rs.getLong("id"));
            salary.setEmployeeId(rs.getLong("employee_id"));
            salary.setBaseSalary(rs.getBigDecimal("base_salary"));
            salary.setBonus(rs.getBigDecimal("bonus"));
            salary.setBenefits(rs.getBigDecimal("benefits"));
            salary.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            salary.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            return salary;
        }
    }
}
