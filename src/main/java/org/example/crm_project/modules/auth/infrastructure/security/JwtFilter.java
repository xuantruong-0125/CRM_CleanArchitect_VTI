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
import java.util.Set;

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

        // 🌟 BYPASS AUTHENTICATION FOR POSTMAN TESTING
        if (true) {
            List<SimpleGrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("LEAD_VIEW"),
                    new SimpleGrantedAuthority("LEAD_CREATE"),
                    new SimpleGrantedAuthority("LEAD_UPDATE"),
                    new SimpleGrantedAuthority("LEAD_DELETE"),
                    new SimpleGrantedAuthority("CUSTOMER_VIEW"),
                    new SimpleGrantedAuthority("CUSTOMER_CREATE"),
                    new SimpleGrantedAuthority("CUSTOMER_UPDATE"),
                    new SimpleGrantedAuthority("CUSTOMER_DELETE"),
                    new SimpleGrantedAuthority("TASK_CREATE"),
                    new SimpleGrantedAuthority("TASK_VIEW"),
                    new SimpleGrantedAuthority("TASK_UPDATE"),
                    new SimpleGrantedAuthority("ACTIVITY_VIEW"),
                    new SimpleGrantedAuthority("ACTIVITY_CREATE"),
                    new SimpleGrantedAuthority("ACTIVITY_UPDATE")
            );
            AuthUser dummyUser = new AuthUser(
                    1L, "admin_test", "Admin Test User", "", true,
                    Set.of("ADMIN"), Set.of("LEAD_VIEW", "LEAD_CREATE", "LEAD_UPDATE", "LEAD_DELETE",
                            "CUSTOMER_VIEW", "CUSTOMER_CREATE", "CUSTOMER_UPDATE", "CUSTOMER_DELETE",
                            "TASK_CREATE", "TASK_VIEW", "TASK_UPDATE",
                            "ACTIVITY_VIEW", "ACTIVITY_CREATE", "ACTIVITY_UPDATE"), "ALL"
            );
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(dummyUser, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
            return;
        }

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