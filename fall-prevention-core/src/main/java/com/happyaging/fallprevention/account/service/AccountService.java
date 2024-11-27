package com.happyaging.fallprevention.account.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.account.persistence.AccountRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;

	@Transactional
	public void register(@Valid Account account) {
		accountRepository.save(account);
	}
}
