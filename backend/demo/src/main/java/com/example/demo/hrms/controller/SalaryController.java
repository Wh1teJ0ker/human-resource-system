package com.example.demo.hrms.controller;

import com.example.demo.hrms.model.Salary;
import com.example.demo.hrms.service.SalaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping
    public ResponseEntity<List<Salary>> getAllSalaries() {
        return ResponseEntity.ok(salaryService.getAllSalaries());
    }

    @PostMapping
    public ResponseEntity<String> addSalary(@RequestBody Salary salary) {
        salaryService.addSalary(salary);
        return ResponseEntity.status(201).body("薪资记录添加成功");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSalary(@PathVariable Long id, @RequestBody Salary updatedSalary) {
        salaryService.updateSalary(id, updatedSalary);
        return ResponseEntity.ok("薪资记录编辑成功");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSalary(@PathVariable Long id) {
        salaryService.deleteSalary(id);
        return ResponseEntity.ok("薪资记录删除成功");
    }
}
