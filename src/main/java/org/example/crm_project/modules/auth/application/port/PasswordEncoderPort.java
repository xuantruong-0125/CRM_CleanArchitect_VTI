package org.example.crm_project.modules.auth.application.port;

public interface PasswordEncoderPort {

    boolean matches(String rawPassword, String encodedPassword);
}