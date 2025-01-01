package com.happyaging.fallprevention.senior.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.senior.entity.Senior;
import com.happyaging.fallprevention.senior.exception.SeniorNotFoundException;
import com.happyaging.fallprevention.senior.persistence.SeniorRepository;
import com.happyaging.fallprevention.senior.usecase.SeniorAdminUseCase;
import com.happyaging.fallprevention.senior.usecase.SeniorUserUseCase;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorCreateDto;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorReadDto;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorUpdateDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeniorAdminService implements SeniorAdminUseCase {
	private final SeniorRepository seniorRepository;

	@Override
	public List<SeniorReadDto> getSeniors() {
		List<Senior> seniors = seniorRepository.findAll();
		return SeniorReadDto.fromEntities(seniors);
	}
}
