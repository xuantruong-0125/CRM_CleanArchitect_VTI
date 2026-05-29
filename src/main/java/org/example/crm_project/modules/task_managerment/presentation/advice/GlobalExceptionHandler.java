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
        
        errorResponse.put("error", "Dữ liệu không hợp lệ");
        errorResponse.put("message", ex.getMessage()); 
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
