package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
    @EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Cho phép đăng ký/đăng nhập
                .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("USER", "ADMIN") // Cả 2 đều được xem
                .requestMatchers("/api/students/**").hasRole("ADMIN") // Chỉ ADMIN mới được POST, PUT, DELETE
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults()); // Sử dụng Basic Auth hoặc JWT tùy nhu cầu
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}