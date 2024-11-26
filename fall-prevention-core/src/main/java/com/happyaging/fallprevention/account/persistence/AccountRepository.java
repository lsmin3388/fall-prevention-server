package com.happyaging.fallprevention.account.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.account.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
