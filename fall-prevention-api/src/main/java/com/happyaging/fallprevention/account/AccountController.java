package com.happyaging.fallprevention.account;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.account.usecase.AccountUseCase;
import com.happyaging.fallprevention.account.usecase.dto.AccountReadDto;
import com.happyaging.fallprevention.annotation.LoggedInUser;
import com.happyaging.fallprevention.security.details.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
	private final AccountUseCase accountUseCase;

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public AccountReadDto getMyAccount(
		@LoggedInUser PrincipalDetails principalDetails
	) {
		return accountUseCase.getMyAccount(principalDetails.account());
	}
}
