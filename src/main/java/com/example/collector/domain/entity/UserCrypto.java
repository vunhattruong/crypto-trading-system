package com.example.collector.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserCrypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   userId;
    private String username;
    private String email;
    private String password;
}

