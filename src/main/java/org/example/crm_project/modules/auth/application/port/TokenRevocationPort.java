package org.example.crm_project.modules.auth.application.port;

public interface TokenRevocationPort {

    void revoke(String token);
}