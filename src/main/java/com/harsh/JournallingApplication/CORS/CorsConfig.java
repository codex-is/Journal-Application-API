package com.harsh.JournallingApplication.CORS;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // apply to all routes
                        .allowedOriginPatterns("*") // allow all origins (http, https, localhost, etc.)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // allow all common methods
                        .allowedHeaders("*") // allow all headers
                        .exposedHeaders("Authorization", "Content-Type") // optional
                        .allowCredentials(true) // allow cookies/auth headers if needed
                        .maxAge(3600); // cache preflight response for 1 hour
            }
        };
    }
}
