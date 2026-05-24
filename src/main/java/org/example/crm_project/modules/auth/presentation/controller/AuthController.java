package org.example.crm_project.modules.auth.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.auth.application.dto.LoginRequest;
import org.example.crm_project.modules.auth.application.dto.LoginResponse;
import org.example.crm_project.modules.auth.application.dto.LogoutRequest;
import org.example.crm_project.modules.auth.application.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String header) {

        String token = (header != null && header.startsWith("Bearer "))
                ? header.substring(7)
                : null;

        authService.logout(new LogoutRequest(token));

        return ResponseEntity.ok().build();
    }
}