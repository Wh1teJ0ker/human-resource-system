package com.example.demo.hrms.controller;

import com.example.demo.hrms.service.DashboardService;
import com.example.demo.hrms.model.DashboardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<?> getDashboardData() {
        try {
            // 获取控制面板数据
            DashboardData dashboardData = dashboardService.getDashboardData();

            // 返回成功响应
            return ResponseEntity.ok(dashboardData);
        } catch (Exception e) {
            // 处理错误，返回 500 错误
            return ResponseEntity.status(500).body(new ErrorResponse("服务器错误，请稍后重试"));
        }
    }

    // 错误响应的结构
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
