package com.example.demo.hrms.service;

import com.example.demo.hrms.model.DashboardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
@Service
public class DashboardService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 获取控制面板数据
    public DashboardData getDashboardData() {
        // 查询员工数量
        String employeeCountSql = "SELECT COUNT(*) FROM employees";
        int employeeCount = jdbcTemplate.queryForObject(employeeCountSql, Integer.class);

        // 查询部门数量
        String departmentCountSql = "SELECT COUNT(*) FROM departments";
        int departmentCount = jdbcTemplate.queryForObject(departmentCountSql, Integer.class);

        // 查询出勤率（计算出勤员工占比）
        String attendanceRateSql = "SELECT COUNT(*) FROM attendances WHERE attendance_status = 'present'";
        int presentCount = jdbcTemplate.queryForObject(attendanceRateSql, Integer.class);

        // 查询总的出勤记录数量
        String totalAttendanceSql = "SELECT COUNT(*) FROM attendances";
        int totalAttendanceCount = jdbcTemplate.queryForObject(totalAttendanceSql, Integer.class);

        // 计算出勤率：如果没有出勤记录，则设置为0，避免除以0的错误
        double attendanceRate = 0;
        if (totalAttendanceCount > 0) {
            attendanceRate = (double) presentCount / totalAttendanceCount * 100;
        }

        // 使用 DecimalFormat 保留一位小数
        DecimalFormat df = new DecimalFormat("#.0");
        String formattedAttendanceRate = df.format(attendanceRate);

        // 查询待审批的请假请求数量
        String leaveRequestsSql = "SELECT COUNT(*) FROM leave_requests WHERE leave_status = 'pending'";
        int leaveRequests = jdbcTemplate.queryForObject(leaveRequestsSql, Integer.class);

        // 返回封装好的数据
        return new DashboardData(employeeCount, departmentCount, Double.parseDouble(formattedAttendanceRate), leaveRequests);
    }
}
