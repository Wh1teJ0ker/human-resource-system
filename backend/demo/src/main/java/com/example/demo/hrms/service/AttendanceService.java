package com.example.demo.hrms.service;

import com.example.demo.hrms.model.Attendance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AttendanceService {

    private final JdbcTemplate jdbcTemplate;

    public AttendanceService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 获取所有考勤记录
    public List<Attendance> getAllAttendanceRecords() {
        String sql = "SELECT * FROM attendances";
        return jdbcTemplate.query(sql, new AttendanceRowMapper());
    }

    // 添加考勤记录
    public void addAttendanceRecord(Attendance attendance) {
        String sql = "INSERT INTO attendances (employee_id, attendance_date, attendance_status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, attendance.getEmployeeId(), attendance.getAttendanceDate(), attendance.getAttendanceStatus());
    }

    // 编辑考勤记录
    public void updateAttendanceRecord(Long id, Attendance updatedAttendance) {
        String sql = "UPDATE attendances SET employee_id = ?, attendance_date = ?, attendance_status = ? WHERE id = ?";
        jdbcTemplate.update(sql, updatedAttendance.getEmployeeId(), updatedAttendance.getAttendanceDate(), updatedAttendance.getAttendanceStatus(), id);
    }

    // 删除考勤记录
    public void deleteAttendanceRecord(Long id) {
        String sql = "DELETE FROM attendances WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // RowMapper 实现
    private static class AttendanceRowMapper implements RowMapper<Attendance> {
        @Override
        public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
            Attendance attendance = new Attendance();
            attendance.setId(rs.getLong("id"));
            attendance.setEmployeeId(rs.getLong("employee_id"));
            attendance.setAttendanceDate(rs.getDate("attendance_date").toLocalDate());
            attendance.setAttendanceStatus(rs.getString("attendance_status"));
            return attendance;
        }
    }
}
