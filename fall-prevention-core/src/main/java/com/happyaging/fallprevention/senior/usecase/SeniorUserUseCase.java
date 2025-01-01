package com.happyaging.fallprevention.senior.usecase;

import java.util.List;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorCreateDto;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorReadDto;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorUpdateDto;

public interface SeniorUserUseCase {
	Long createSenior(Account account, SeniorCreateDto seniorCreateDto);
	Long updateSenior(Long seniorId, SeniorUpdateDto seniorUpdateDto);
	void deleteSenior(Long seniorId);
	List<SeniorReadDto> getMySeniors(Account account);
}
