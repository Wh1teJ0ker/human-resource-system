package com.example.demo.hrms.controller;

import com.example.demo.hrms.model.Department;
import com.example.demo.hrms.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // 获取部门列表
    @GetMapping
    public ResponseEntity<?> getDepartments() {
        try {
            List<Department> departments = departmentService.getDepartmentList();
            return ResponseEntity.ok(departments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"获取部门列表失败\"}");
        }
    }

    // 添加部门
    @PostMapping
    public ResponseEntity<?> addDepartment(@RequestBody Department department) {
        try {
            departmentService.addDepartment(department);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"部门添加成功\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"添加部门失败\"}");
        }
    }

    // 编辑部门
    @PutMapping("/{id}")
    public ResponseEntity<?> editDepartment(@PathVariable Long id, @RequestBody Department department) {
        try {
            departmentService.editDepartment(id, department);
            return ResponseEntity.ok("{\"message\": \"部门编辑成功\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"编辑部门失败\"}");
        }
    }

    // 删除部门
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok("{\"message\": \"部门删除成功\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"删除部门失败\"}");
        }
    }
}
