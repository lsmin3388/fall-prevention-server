package com.happyaging.fallprevention.auth.usecase.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.account.entity.AccountRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequestDto(
	@NotBlank(message = "이메일은 필수값입니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	String email,

	@NotBlank(message = "비밀번호는 필수값입니다.")
	@Pattern(
		regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
		message = "비밀번호는 최소 8자리이며, 대소문자, 숫자, 특수문자를 포함해야 합니다."
	)
	String password,

	@NotBlank(message = "이름은 필수값입니다.")
	String name,

	@NotBlank(message = "휴대폰 번호는 필수값입니다.")
	@Pattern(
		regexp = "^010-\\d{4}-\\d{4}$",
		message = "휴대폰 번호 양식이 올바르지 않습니다. 예) 010-1234-5678"
	)
	String phoneNumber
) {

	public Account toEntity(PasswordEncoder passwordEncoder) {
		return Account.builder()
			.email(email)
			.password(passwordEncoder.encode(password))
			.username(name)
			.phoneNumber(phoneNumber)
			.role(AccountRole.USER)
			.build();
	}
}
