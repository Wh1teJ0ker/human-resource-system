package com.example.demo.hrms.model;

public class DashboardData {

    private int employeeCount;     // 当前员工数量
    private int departmentCount;   // 部门数量
    private double attendanceRate; // 出勤率（百分比）
    private int leaveRequests;     // 请假请求数量

    public DashboardData(int employeeCount, int departmentCount, double attendanceRate, int leaveRequests) {
        this.employeeCount = employeeCount;
        this.departmentCount = departmentCount;
        this.attendanceRate = attendanceRate;
        this.leaveRequests = leaveRequests;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public int getDepartmentCount() {
        return departmentCount;
    }

    public double getAttendanceRate() {
        return attendanceRate;
    }

    public int getLeaveRequests() {
        return leaveRequests;
    }
}
