package com.happyaging.fallprevention.account.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByEmail(String email);
	boolean existsByEmail(String email);
}
