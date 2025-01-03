package com.example.demo.hrms.model;

public class Department {

    private Long id;
    private String departmentName;

    // 构造方法
    public Department(Long id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
