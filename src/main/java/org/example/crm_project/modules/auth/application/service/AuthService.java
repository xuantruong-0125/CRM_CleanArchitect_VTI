package org.example.crm_project.modules.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.auth.application.dto.LoginRequest;
import org.example.crm_project.modules.auth.application.dto.LoginResponse;
import org.example.crm_project.modules.auth.application.dto.LogoutRequest;
import org.example.crm_project.modules.auth.application.port.PasswordEncoderPort;
import org.example.crm_project.modules.auth.application.port.TokenProvider;
import org.example.crm_project.modules.auth.application.port.TokenRevocationPort;
import org.example.crm_project.modules.auth.domain.exception.InvalidCredentialsException;
import org.example.crm_project.modules.auth.domain.exception.UserNotFoundException;
import org.example.crm_project.modules.auth.domain.entity.AuthUser;
import org.example.crm_project.modules.auth.domain.repository.UserAuthRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenProvider tokenProvider;
    private final TokenRevocationPort tokenRevocationPort;

    public LoginResponse login(LoginRequest request) {

        AuthUser user = userAuthRepository
                .findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if (!user.isActive()) {
            throw new InvalidCredentialsException();
        }

        boolean isMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!isMatch) {
            throw new InvalidCredentialsException();
        }

        String accessToken = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken,
                user.getUsername(),
                user.getFullname(),
                user.getRoles(),
                user.getScope()
        );

    }

    public void logout(LogoutRequest request) {

        if (request.getAccessToken() == null || request.getAccessToken().isBlank()) {
            return;
        }
        tokenRevocationPort.revoke(request.getAccessToken());
    }
}