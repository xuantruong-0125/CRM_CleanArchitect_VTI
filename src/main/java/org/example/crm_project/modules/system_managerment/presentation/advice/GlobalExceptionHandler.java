package org.example.crm_project.modules.system_managerment.presentation.advice;

import org.example.crm_project.modules.system_managerment.domain.exception.*;
import org.example.crm_project.modules.system_managerment.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===== NOT FOUND =====
    @ExceptionHandler({
            OrganizationNotFoundException.class,
            MenuNotFoundException.class,
            RoleNotFoundException.class,
            RoleMenuPermissionNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(Exception ex) {
        return ex.getMessage();
    }

    // ===== BAD REQUEST =====
    @ExceptionHandler({
            InvalidPermissionException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(Exception ex) {
        return ex.getMessage();
    }

    // ===== INTERNAL ERROR =====
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleOther(Exception ex) {
        return "Internal error: " + ex.getMessage();
    }
}