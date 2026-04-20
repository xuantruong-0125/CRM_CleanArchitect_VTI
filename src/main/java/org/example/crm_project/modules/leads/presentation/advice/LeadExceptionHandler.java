package org.example.crm_project.modules.leads.presentation.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.example.crm_project.modules.leads.domain.exception.InvalidLeadException;
import org.example.crm_project.modules.leads.domain.exception.LeadNotFoundException;
import org.example.crm_project.modules.leads.presentation.dto.LeadErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class LeadExceptionHandler {

    @ExceptionHandler(LeadNotFoundException.class)
    public ResponseEntity<LeadErrorResponse> handleNotFound(LeadNotFoundException exception, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, "LEAD_NOT_FOUND", exception.getMessage(), request);
    }

    @ExceptionHandler({InvalidLeadException.class, IllegalArgumentException.class})
    public ResponseEntity<LeadErrorResponse> handleBadRequest(RuntimeException exception, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "INVALID_LEAD_REQUEST", exception.getMessage(), request);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<LeadErrorResponse> handleInvalidRequest(Exception exception, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "INVALID_REQUEST_FORMAT", exception.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<LeadErrorResponse> handleOther(Exception exception, HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Internal error: " + exception.getMessage(), request);
    }

    private ResponseEntity<LeadErrorResponse> build(HttpStatus status, String errorCode, String message, HttpServletRequest request) {
        return ResponseEntity.status(status).body(LeadErrorResponse.builder()
                .status(status.value())
                .errorCode(errorCode)
                .message(message)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build());
    }
}