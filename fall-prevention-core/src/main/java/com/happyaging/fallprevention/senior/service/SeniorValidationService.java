package com.happyaging.fallprevention.senior.service;

import org.springframework.stereotype.Service;

import com.happyaging.fallprevention.senior.persistence.SeniorRepository;
import com.happyaging.fallprevention.senior.usecase.SeniorValidationUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeniorValidationService implements SeniorValidationUseCase {
	private final SeniorRepository seniorRepository;

	@Override
	public boolean isMySenior(Long accountId, Long seniorId) {
		return seniorRepository.existsByIdAndAccountId(seniorId, accountId);
	}
}
