package com.happyaging.fallprevention.account.service;

import org.springframework.stereotype.Service;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.account.usecase.AccountUseCase;
import com.happyaging.fallprevention.account.usecase.dto.AccountReadDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountUseCase {
	@Override
	public AccountReadDto getMyAccount(Account account) {
		return AccountReadDto.fromEntity(account);
	}
}
