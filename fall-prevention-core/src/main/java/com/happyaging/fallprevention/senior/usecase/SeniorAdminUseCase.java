package com.happyaging.fallprevention.senior.usecase;

import java.util.List;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorCreateDto;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorReadDto;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorUpdateDto;

public interface SeniorAdminUseCase {
	List<SeniorReadDto> getSeniors();
}
