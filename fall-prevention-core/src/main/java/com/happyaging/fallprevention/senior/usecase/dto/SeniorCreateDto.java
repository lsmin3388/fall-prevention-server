package com.happyaging.fallprevention.senior.usecase.dto;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.senior.entity.Relationship;
import com.happyaging.fallprevention.senior.entity.Senior;
import com.happyaging.fallprevention.senior.entity.SeniorSex;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SeniorCreateDto(
	@NotBlank(message = "이름은 필수 항목입니다.")
	@Size(max = 50, message = "이름은 최대 10자 이내로 입력해야 합니다.")
	String name,

	@NotBlank(message = "주소는 필수 항목입니다.")
	@Size(max = 255, message = "주소는 최대 255자 이내로 입력해야 합니다.")
	String address,

	@NotNull(message = "출생 연도는 필수 항목입니다.")
	@Min(value = 1900, message = "출생 연도는 1900년 이후여야 합니다.")
	@Max(value = 2100, message = "출생 연도는 2100년 이전이어야 합니다.")
	Integer birthYear,

	@NotNull(message = "성별은 필수 항목입니다.")
	SeniorSex sex,

	@NotBlank(message = "전화번호는 필수 항목입니다.")
	@Pattern(regexp = "^010-\\d{3,4}-\\d{4}$", message = "전화번호는 010-XXXX-XXXX 형식이어야 합니다.")
	String phoneNumber,

	@NotNull(message = "관계는 필수 항목입니다.")
	Relationship relationship,

	@Size(max = 500, message = "메모는 최대 500자 이내로 입력해야 합니다.")
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
