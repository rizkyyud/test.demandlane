package com.example.test.demandlane.model.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "member")
public class Member {
    @Id
    private Long id;
    private String name;
    private String email;
}
