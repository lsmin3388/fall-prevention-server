package com.happyaging.fallprevention.account.usecase;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.account.usecase.dto.AccountReadDto;

public interface AccountUseCase {
	AccountReadDto getMyAccount(Account account);
}
