package com.happyaging.fallprevention.senior.usecase.dto;

import com.happyaging.fallprevention.senior.entity.Relationship;
import com.happyaging.fallprevention.senior.entity.SeniorSex;

public record SeniorUpdateDto(
	String name,
	String address,
	Integer birthYear,
	SeniorSex sex,
	String phoneNumber,
	Relationship relationship,
	String memo
) {
}
