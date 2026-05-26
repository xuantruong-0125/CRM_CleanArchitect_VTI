package org.example.crm_project.modules.task_managerment.presentation.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        
        // Trả về đúng câu thông báo mà file Task.java đã ném ra
        errorResponse.put("error", "Dữ liệu không hợp lệ");
        errorResponse.put("message", ex.getMessage()); 
        
        // Bắn về HTTP 400 thay vì 500
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
