package com.example.collector.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.collector.domain.entity.UserCrypto;

public interface UserRepository extends JpaRepository<UserCrypto, Long> {
}