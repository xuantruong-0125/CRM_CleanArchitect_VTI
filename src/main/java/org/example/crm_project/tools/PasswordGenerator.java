package org.example.crm_project.tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String raw = "123456";
        String encoded = new BCryptPasswordEncoder().encode(raw);

        System.out.println("RAW: " + raw);
        System.out.println("ENCODED: " + encoded);
        System.out.println(encoder.matches(raw, encoded));
    }
}