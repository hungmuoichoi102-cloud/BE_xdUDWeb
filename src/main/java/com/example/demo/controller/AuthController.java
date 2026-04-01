package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"https://users-management-topaz.vercel.app","http://192.168.1.3:5173"})
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        if (userService.validateUserCredentials(authRequest.getUsername(), authRequest.getPassword())) {
            User user = userService.getUserByUsername(authRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse("Login successful", user));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Invalid username or password", null));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User existingUser = userService.getUserByUsername(user.getUsername());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponse("Username already exists", null));
        }
        user.setRole("ROLE_USER");
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse("Registration successful", createdUser));
    }
}
