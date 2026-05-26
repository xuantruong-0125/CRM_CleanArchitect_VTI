package org.example.crm_project.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Bật tính năng nhận cookie/auth headers từ frontend
        config.setAllowCredentials(true);

        // Khi dùng setAllowCredentials(true), BẮT BUỘC phải dùng OriginPattern thay vì Origin thường
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // Áp dụng cấu hình này cho tất cả các endpoint trong dự án
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}