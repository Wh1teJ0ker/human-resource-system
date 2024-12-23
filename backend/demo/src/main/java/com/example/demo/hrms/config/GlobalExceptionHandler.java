package com.example.demo.hrms.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

// 自定义异常类
class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}

@ControllerAdvice
public class GlobalExceptionHandler {

    // 处理通用的运行时异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        // 打印日志或者记录异常
        e.printStackTrace();

        // 返回自定义的错误响应
        return new ResponseEntity<>(
                new ErrorResponse("服务器错误", "500"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // 处理自定义的异常
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return new ResponseEntity<>(
                new ErrorResponse("自定义异常", "400"),
                HttpStatus.BAD_REQUEST
        );
    }

    // 错误响应的结构
    public static class ErrorResponse {
        private String message;
        private String code;

        // 构造器：只需要 message 和 code
        public ErrorResponse(String message, String code) {
            this.message = message;
            this.code = code;
        }

        // Getters 和 Setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
