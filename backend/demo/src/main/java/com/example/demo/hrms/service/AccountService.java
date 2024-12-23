package com.example.demo.hrms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
}
