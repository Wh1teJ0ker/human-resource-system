package com.example.demo.hrms.controller;

import com.example.demo.hrms.model.Attendance;
import com.example.demo.hrms.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendanceRecords() {
        return ResponseEntity.ok(attendanceService.getAllAttendanceRecords());
    }

    @PostMapping
    public ResponseEntity<String> addAttendanceRecord(@RequestBody Attendance attendance) {
        attendanceService.addAttendanceRecord(attendance);
        return ResponseEntity.status(201).body("考勤记录添加成功");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAttendanceRecord(@PathVariable Long id, @RequestBody Attendance attendance) {
        attendanceService.updateAttendanceRecord(id, attendance);
        return ResponseEntity.ok("考勤记录编辑成功");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendanceRecord(@PathVariable Long id) {
        attendanceService.deleteAttendanceRecord(id);
        return ResponseEntity.ok("考勤记录删除成功");
    }
}
