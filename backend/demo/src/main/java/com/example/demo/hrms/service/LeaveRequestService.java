package com.example.demo.hrms.service;

import com.example.demo.hrms.model.LeaveRequest;  // 导入 LeaveRequest 类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 构造函数: 没有返回类型，名称与类名相同
    public LeaveRequestService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 获取所有请假请求
    public List<LeaveRequest> getAllLeaveRequests() {
        String sql = "SELECT * FROM leave_requests";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LeaveRequest.class));
        } catch (Exception e) {
            // 记录异常
            System.err.println("Error fetching leave requests: " + e.getMessage());
            throw new RuntimeException("Error fetching leave requests", e);  // 或者自定义异常
        }
    }

    // 提交请假请求
    public LeaveRequest addLeaveRequest(LeaveRequest leaveRequest) {
        String sql = "INSERT INTO leave_requests (employee_id, leave_date, leave_type, leave_status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                leaveRequest.getEmployeeId(),
                leaveRequest.getLeaveDate(),  // 使用 LocalDate 类型
                leaveRequest.getLeaveType().name(),
                leaveRequest.getLeaveStatus().name(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // 获取插入后的ID
        String selectSql = "SELECT LAST_INSERT_ID()";
        Long id = jdbcTemplate.queryForObject(selectSql, Long.class);
        leaveRequest.setId(id);
        leaveRequest.setCreatedAt(LocalDateTime.now());
        leaveRequest.setUpdatedAt(LocalDateTime.now());

        return leaveRequest;
    }

    // 编辑请假请求（审批）
    public LeaveRequest updateLeaveRequest(Long id, LeaveRequest leaveRequest) {
        String sql = "UPDATE leave_requests SET leave_status = ?, updated_at = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql,
                leaveRequest.getLeaveStatus().name(),
                LocalDateTime.now(),
                id
        );

        if (rowsUpdated > 0) {
            leaveRequest.setId(id);
            leaveRequest.setUpdatedAt(LocalDateTime.now());
            return leaveRequest;
        }
        return null; // 或抛出异常
    }

    // 删除请假请求
    public void deleteLeaveRequest(Long id) {
        String sql = "DELETE FROM leave_requests WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
