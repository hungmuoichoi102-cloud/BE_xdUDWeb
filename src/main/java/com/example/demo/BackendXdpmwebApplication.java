package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;

@SpringBootApplication
public class BackendXdpmwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendXdpmwebApplication.class, args);
	}
	@Bean
CommandLineRunner initDatabase(UserRepository repository) {
    return args -> {
        if (repository.count() == 0) {
            User user1 = new User();
            user1.setName("Nguyen Van A");
            user1.setUsername("nguyenvana");
            user1.setPassword("password123");
            user1.setRole("USER");
            repository.save(user1);
            
            User user2 = new User();
            user2.setName("Le Thi B");
            user2.setUsername("lethib");
            user2.setPassword("password456");
            user2.setRole("ADMIN");
            repository.save(user2);
        }
    };
}
}

