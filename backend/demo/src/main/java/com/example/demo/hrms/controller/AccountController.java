package com.example.demo.hrms.controller;

import com.example.demo.hrms.service.AccountService;
import com.example.demo.hrms.model.Account;
import com.example.demo.hrms.config.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody Account account) {
        try {
            // 调用服务层逻辑
            accountService.updateAccountPassword(account.getUsername(), account.getPassword());

            // 返回成功响应，message 放成功信息，code 放状态码
            return ResponseEntity.ok(new GlobalExceptionHandler.ErrorResponse("管理员账户修改成功", "200"));
        } catch (IllegalArgumentException e) {
            // 参数验证失败，返回 400 错误
            return ResponseEntity.badRequest().body(new GlobalExceptionHandler.ErrorResponse("用户名或密码不能为空", "400"));
        } catch (Exception e) {
            // 服务器错误，返回 500 错误
            return ResponseEntity.status(500).body(new GlobalExceptionHandler.ErrorResponse("服务器错误，请稍后重试", "500"));
        }
    }
}
