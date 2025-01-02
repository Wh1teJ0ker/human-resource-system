package com.example.demo.hrms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateAccountPassword(String username, String password) {
        // 业务逻辑：验证用户名和密码不能为空
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名或密码不能为空");
        }

        // 获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTimestamp = currentDateTime.format(formatter);

        // 执行更新操作：更新密码和更新时间
        String updateSql = "UPDATE accounts SET password = ?, updated_at = ? WHERE username = ?";
        int rowsAffected = jdbcTemplate.update(updateSql, password, currentTimestamp, username);

        // 检查是否更新成功
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("用户名不存在，更新失败");
        }

        // 其他业务逻辑
        System.out.println("管理员账户用户名: " + username + " 密码已更新为: " + password);
    }



    /**
     * 用户注册
     */
    public void register(String username, String password, String role) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名或密码不能为空");
        }

        String checkSql = "SELECT COUNT(*) FROM accounts WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, username);

        if (count != null && count > 0) {
            throw new IllegalArgumentException("用户名已存在");
        }

        String insertSql = "INSERT INTO accounts (username, password, role, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTimestamp = currentDateTime.format(formatter);

        jdbcTemplate.update(insertSql, username, password, role, currentTimestamp, currentTimestamp);
    }



    /**
     * 用户登录
     */
    public String login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名或密码不能为空");
        }
        // 查询用户名对应的密码和角色信息
        String querySql = "SELECT password, role FROM accounts WHERE username = ?";
        return jdbcTemplate.queryForObject(querySql, (rs, rowNum) -> {
            String storedPassword = rs.getString("password");

            // 检查密码是否匹配（直接比较明文密码）
            if (password.equals(storedPassword)) {
                return rs.getString("role"); // 返回角色信息
            } else {
                throw new IllegalArgumentException("用户名或密码错误");
            }
        }, username);
    }


//    public Map<String, Object> getAccountDetailsById(Long accountId) {
//        String sql = "SELECT a.username, 'USER' AS role, e.name AS employee_name, d.department_name, " +
//                "s.base_salary, s.bonus, s.benefits " +
//                "FROM accounts a " +
//                "JOIN employees e ON a.id = e.id " +
//                "JOIN departments d ON e.department_id = d.id " +
//                "JOIN salaries s ON e.id = s.employee_id " +
//                "WHERE a.id = ?";
//
//        return jdbcTemplate.queryForMap(sql, accountId);
//    }
public Map<String, Object> getAccountDetailsByUsername(String username) {
    String sql = "SELECT a.username, 'USER' AS role, e.name AS employee_name, d.department_name, " +
            "s.base_salary, s.bonus, s.benefits " +
            "FROM accounts a " +
            "JOIN employees e ON a.id = e.id " +
            "JOIN departments d ON e.department_id = d.id " +
            "JOIN salaries s ON e.id = s.employee_id " +
            "WHERE a.username = ?";

    return jdbcTemplate.queryForMap(sql, username);
}


}
