package com.projeto.sistema.system;

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
        
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("https://localhost:4200"); // ← Adicione esta linha
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("https://localhost:8080");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Access-Control-Allow-Origin");
        config.addExposedHeader("Access-Control-Allow-Credentials");

        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}