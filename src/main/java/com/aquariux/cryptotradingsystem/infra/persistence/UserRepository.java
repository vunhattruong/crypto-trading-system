package com.aquariux.cryptotradingsystem.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aquariux.cryptotradingsystem.domain.entity.UserCrypto;

public interface UserRepository extends JpaRepository<UserCrypto, Long> {
}