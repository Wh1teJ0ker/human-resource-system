package com.example.demo.hrms.controller;

import com.example.demo.hrms.service.EmployeeService;
import com.example.demo.hrms.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 获取员工列表
    @GetMapping
    public ResponseEntity<?> getEmployeeList() {
        try {
            List<Employee> employeeList = employeeService.getEmployeeList();
            return ResponseEntity.ok(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("获取员工列表失败"));
        }
    }

    // 添加员工
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        try {
            employeeService.addEmployee(employee);
            return ResponseEntity.status(201).body(new ErrorResponse("员工添加成功"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("添加员工失败"));
        }
    }

    // 编辑员工
    @PutMapping("/{id}")
    public ResponseEntity<?> editEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            employeeService.editEmployee(id, employee);
            return ResponseEntity.ok(new ErrorResponse("员工信息更新成功"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse("编辑员工失败"));
        }
    }

    // 删除员工
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok(new ErrorResponse("员工删除成功"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("删除员工失败"));
        }
    }

    // 错误响应类
    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
