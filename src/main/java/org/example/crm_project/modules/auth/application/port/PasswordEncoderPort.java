package org.example.crm_project.modules.auth.application.port;

public interface PasswordEncoderPort {

    String encode(String rawPassword);

    boolean matches(
            String rawPassword,
            String encodedPassword
    );
}