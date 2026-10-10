package com.example.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/actuator/health",
                    "/actuator/info"
                ).permitAll()

                // User administration
                .requestMatchers("/api/users/**")
                    .hasRole("ADMIN")

                // Category reads: any authenticated account
                .requestMatchers(
                    HttpMethod.GET, "/api/categories/**"
                ).hasAnyRole("USER", "AGENT", "ADMIN")

                .requestMatchers("/api/categories/**")
                    .hasRole("ADMIN")

                // Agent directory: authenticated accounts
                .requestMatchers(
                    HttpMethod.GET, "/api/agents/**"
                ).hasAnyRole("USER", "AGENT", "ADMIN")

                .requestMatchers("/api/agents/**")
                    .hasRole("ADMIN")

                // Complaint operations
                .requestMatchers(
                    HttpMethod.POST, "/api/complaints/**"
                ).hasAnyRole("USER", "ADMIN")

                .requestMatchers(
                    HttpMethod.PATCH, "/api/complaints/*/assign/*"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PATCH, "/api/complaints/*/status"
                ).hasAnyRole("AGENT", "ADMIN")

                .requestMatchers("/api/complaints/**")
                    .hasAnyRole("USER", "AGENT", "ADMIN")

                // Comments
                .requestMatchers("/api/comments/**")
                    .hasAnyRole("USER", "AGENT", "ADMIN")

                // Resolutions
                .requestMatchers("/api/resolutions/**")
                    .hasAnyRole("AGENT", "ADMIN")

                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}