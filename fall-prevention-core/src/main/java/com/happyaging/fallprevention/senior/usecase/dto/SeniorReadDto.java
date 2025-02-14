package com.happyaging.fallprevention.senior.usecase.dto;

import java.util.List;

import com.happyaging.fallprevention.senior.entity.Relationship;
import com.happyaging.fallprevention.senior.entity.Senior;
import com.happyaging.fallprevention.senior.entity.SeniorSex;

import lombok.Builder;

@Builder
public record SeniorReadDto(
	Long seniorId,
	String name,
	String address,
	Integer birthYear,
	SeniorSex sex,
	String phoneNumber,
	Relationship relationship,
	String memo
) {
	public static SeniorReadDto fromEntity(Senior senior) {
		return SeniorReadDto.builder()
			.seniorId(senior.getId())
			.name(senior.getName())
			.address(senior.getAddress())
			.birthYear(senior.getBirthYear())
			.sex(senior.getSex())
			.phoneNumber(senior.getPhoneNumber())
			.relationship(senior.getRelation())
			.memo(senior.getMemo())
			.build();
	}

	public static List<SeniorReadDto> fromEntities(List<Senior> seniors) {
		return seniors.stream()
			.map(SeniorReadDto::fromEntity)
			.toList();
	}
}
