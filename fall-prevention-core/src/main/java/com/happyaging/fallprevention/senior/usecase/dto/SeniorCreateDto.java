package com.happyaging.fallprevention.senior.usecase.dto;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.senior.entity.Relationship;
import com.happyaging.fallprevention.senior.entity.Senior;
import com.happyaging.fallprevention.senior.entity.SeniorSex;

public record SeniorCreateDto(
	String name,
	String address,
	Integer birthYear,
	SeniorSex sex,
	String phoneNumber,
	Relationship relationship,
	String memo
) {
	public Senior toEntity(Account account) {
		return Senior.builder()
			.name(name)
			.address(address)
			.birthYear(birthYear)
			.sex(sex)
			.phoneNumber(phoneNumber)
			.relation(relationship)
			.memo(memo)
			.account(account)
			.build();
	}
}
