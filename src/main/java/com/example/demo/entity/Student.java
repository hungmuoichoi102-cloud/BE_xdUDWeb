package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class Student {
    @Id
    private long id;
    private String name;
    private String email;
    private String ngaysinh;
    
}
