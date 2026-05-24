package org.example.crm_project.modules.auth.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.auth.application.port.PasswordEncoderPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCryptPasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public String encode(String rawPassword) {

        return passwordEncoder.encode(rawPassword);
    }

}
