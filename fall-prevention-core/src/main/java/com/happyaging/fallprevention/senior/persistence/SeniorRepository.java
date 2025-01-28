package com.happyaging.fallprevention.senior.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.senior.entity.Senior;

public interface SeniorRepository extends JpaRepository<Senior, Long> {
	List<Senior> findAllByAccount(Account account);
	Optional<Senior> findByAccountAndId(Account account, Long id);
	boolean existsByIdAndAccountId(Long seniorId, Long accountId);
}
