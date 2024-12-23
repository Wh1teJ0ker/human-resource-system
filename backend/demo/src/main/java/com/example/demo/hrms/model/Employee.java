package com.example.demo.hrms.model;

public class Employee {
    private Long id;
    private String name;
    private String position;
    private String department;  // 部门名称
    private Long departmentId;  // 部门ID

    // 构造函数
    public Employee(Long id, String name, String position, String department) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.department = department;
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}

