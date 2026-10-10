package com.example.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createInitialAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            String email = "admin@complaintportal.local";

            if (!userRepository.existsByEmail(email)) {
                User admin = new User();

                admin.setName("Portal Administrator");
                admin.setEmail(email);
                admin.setPassword(
                    passwordEncoder.encode("Admin@12345")
                );
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println(
                    "Initial development admin account created."
                );
            }
        };
    }
}