package org.example.crm_project.modules.auth.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.auth.application.port.TokenProvider;
import org.example.crm_project.modules.auth.domain.entity.AuthUser;
import org.example.crm_project.modules.auth.domain.repository.UserAuthRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserAuthRepository userAuthRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            try {
                // 🔥 1. Validate token
                if (tokenProvider.validateToken(token)) {

                    // 🔥 2. Lấy username từ token
                    String username = tokenProvider.getUsernameFromToken(token);

                    // 🔥 3. Load user từ DB
                    AuthUser user = userAuthRepository
                            .findByUsername(username)
                            .orElse(null);

                    if (user != null && user.isActive()) {

                        // 🔥 4. Convert permission → GrantedAuthority
                        List<SimpleGrantedAuthority> authorities =
                                user.getPermissions().stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .toList();

                        // 🔥 5. Tạo Authentication
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        user,
                                        null,
                                        authorities
                                );

                        // 🔥 6. Set vào SecurityContext
                        SecurityContextHolder.getContext()
                                .setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                // ❗ Không throw exception ở đây → tránh crash toàn bộ request
            }
        }

        filterChain.doFilter(request, response);
    }
}