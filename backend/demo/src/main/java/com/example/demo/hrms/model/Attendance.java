package com.example.demo.hrms.model;

import java.time.LocalDate;

public class Attendance {

    private Long id; // 自动生成的主键
    private Long employeeId; // 外键，引用 employees 表的 id
    private LocalDate attendanceDate; // 考勤日期
    private String attendanceStatus; // 出勤状态（present, absent, late）

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}
