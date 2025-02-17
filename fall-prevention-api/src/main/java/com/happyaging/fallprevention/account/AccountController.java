package com.happyaging.fallprevention.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.account.usecase.AccountUseCase;
import com.happyaging.fallprevention.account.usecase.dto.AccountReadDto;
import com.happyaging.fallprevention.annotation.LoggedInUser;
import com.happyaging.fallprevention.security.details.PrincipalDetails;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
	private final AccountUseCase accountUseCase;

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<AccountReadDto>> getMyAccount(
		@LoggedInUser PrincipalDetails principalDetails
	) {
		AccountReadDto accountReadDto = accountUseCase.getMyAccount(principalDetails.account());
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, accountReadDto));
	}


	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<AccountReadDto>> checkAdminRole(
		@LoggedInUser PrincipalDetails principalDetails
	) {
		AccountReadDto accountReadDto = accountUseCase.getMyAccount(principalDetails.account());
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, accountReadDto));
	}
}
