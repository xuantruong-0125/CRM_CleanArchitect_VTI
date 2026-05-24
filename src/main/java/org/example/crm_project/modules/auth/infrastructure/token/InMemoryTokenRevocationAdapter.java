package org.example.crm_project.modules.auth.infrastructure.token;

import org.example.crm_project.modules.auth.application.port.TokenRevocationPort;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class InMemoryTokenRevocationAdapter implements TokenRevocationPort {

    private final Set<String> blacklist = new HashSet<>();

    @Override
    public void revoke(String token) {
        blacklist.add(token);
    }

    public boolean isRevoked(String token) {
        return blacklist.contains(token);
    }
}