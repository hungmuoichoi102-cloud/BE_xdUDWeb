package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class GradeId implements Serializable {
    private String mssv;
    private String mamh;
    private int lan;
}
