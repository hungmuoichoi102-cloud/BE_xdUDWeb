package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import com.example.demo.service.CustomUserDetailService;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailService userDetailsService;

    public SecurityConfig(CustomUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                //endpoint nào không cần xác thực thì thêm vào đây
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/users/**").permitAll()
                //phân quyền cho sinh viên
                .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/students/**").hasRole("ADMIN")
                //phân quyền cho môn học
                .requestMatchers(HttpMethod.GET, "/api/monhocs/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/monhocs/**").hasRole("ADMIN")
                //phân quyền cho lớp
                .requestMatchers(HttpMethod.GET, "/api/lops/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/lops/**").hasRole("ADMIN")
                //phân quyền cho điểm 
                .requestMatchers(HttpMethod.GET, "/api/grades/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/grades/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}