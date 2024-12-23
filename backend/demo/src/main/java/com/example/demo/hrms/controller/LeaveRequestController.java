package com.example.demo.hrms.controller;

import com.example.demo.hrms.model.LeaveRequest;
import com.example.demo.hrms.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaverequests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    // 获取所有请假请求
    @GetMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }

    // 提交请假请求
    @PostMapping
    public ResponseEntity<String> addLeaveRequest(@RequestBody LeaveRequest leaveRequest) {
        leaveRequestService.addLeaveRequest(leaveRequest);
        return ResponseEntity.status(201).body("请假请求提交成功");
    }

    // 编辑请假请求（审批）
    @PutMapping("/{id}")
    public ResponseEntity<String> updateLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequest leaveRequest) {
        LeaveRequest updatedRequest = leaveRequestService.updateLeaveRequest(id, leaveRequest);
        if (updatedRequest != null) {
            return ResponseEntity.ok("请假请求审批成功");
        }
        return ResponseEntity.status(400).body("审批请假请求失败");
    }

    // 删除请假请求
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
        return ResponseEntity.ok("请假请求删除成功");
    }
}
