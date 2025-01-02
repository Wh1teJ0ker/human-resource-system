package com.example.demo.hrms.controller;

import com.example.demo.hrms.service.AccountService;
import com.example.demo.hrms.model.Account;
import com.example.demo.hrms.config.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

//-------------用户注册-------------------
@PostMapping("/register")
public ResponseEntity<?> register(@RequestBody Account account) {
    try {
        String role = account.getRole() != null ? account.getRole() : "USER";
        accountService.register(account.getUsername(), account.getPassword(), role);
        return ResponseEntity.ok(new GlobalExceptionHandler.ErrorResponse("用户注册成功", "200"));
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new GlobalExceptionHandler.ErrorResponse(e.getMessage(), "400"));
    } catch (Exception e) {
        return ResponseEntity.status(500).body(new GlobalExceptionHandler.ErrorResponse("服务器错误register，请稍后重试", "500"));
    }
}


//-------------用户登入-------------------
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Account account) {
    try {
        String role = accountService.login(account.getUsername(), account.getPassword());
        return ResponseEntity.ok(new GlobalExceptionHandler.ErrorResponse("登录成功，角色: " + role, "200"));
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(401).body(new GlobalExceptionHandler.ErrorResponse(e.getMessage(), "401"));
    } catch (Exception e) {
        return ResponseEntity.status(500).body(new GlobalExceptionHandler.ErrorResponse("服务器错误login，请稍后重试", "500"));
    }
}

//------------获取用户详细信息-----------------
//    @GetMapping("/{id}/details")
//    public ResponseEntity<?> getAccountDetails(@PathVariable Long id) {
//        try {
//            Map<String, Object> accountDetails = accountService.getAccountDetailsById(id);
//            return ResponseEntity.ok(accountDetails);
//        } catch (EmptyResultDataAccessException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new GlobalExceptionHandler.ErrorResponse("账户信息未找到", "404"));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new GlobalExceptionHandler.ErrorResponse("服务器错误，请稍后重试", "500"));
//        }
//    }


    @PostMapping("/details")
    public ResponseEntity<?> getAccountDetails(@RequestBody Account account) {
        try {
            // 从传入的 Account 对象获取用户名
            String username = account.getUsername();
            if (username == null || username.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new GlobalExceptionHandler.ErrorResponse("用户名不能为空", "400"));
            }

            Map<String, Object> accountDetails = accountService.getAccountDetailsByUsername(username);
            return ResponseEntity.ok(accountDetails);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GlobalExceptionHandler.ErrorResponse("账户信息未找到", "404"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GlobalExceptionHandler.ErrorResponse("服务器错误，请稍后重试", "500"));
        }
    }



}
