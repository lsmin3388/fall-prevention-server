package com.happyaging.fallprevention.account.usecase.dto;

import com.happyaging.fallprevention.account.entity.Account;

import lombok.Builder;

@Builder
public record AccountReadDto(
	Long id,
	String email,
	String name,
	String phoneNumber
) {
	public static AccountReadDto fromEntity(Account account) {
		return AccountReadDto.builder()
			.id(account.getId())
			.email(account.getEmail())
			.name(account.getUsername())
			.phoneNumber(account.getPhoneNumber())
			.build();
	}
}
