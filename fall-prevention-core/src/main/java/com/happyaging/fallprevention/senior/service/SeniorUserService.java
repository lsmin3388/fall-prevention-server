package com.happyaging.fallprevention.senior.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.senior.entity.Senior;
import com.happyaging.fallprevention.senior.exception.SeniorNotFoundException;
import com.happyaging.fallprevention.senior.persistence.SeniorRepository;
import com.happyaging.fallprevention.senior.usecase.SeniorUserUseCase;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorCreateDto;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorReadDto;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorUpdateDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeniorUserService implements SeniorUserUseCase {
	private final SeniorRepository seniorRepository;


	@Override
	@Transactional
	public Long createSenior(Account account, SeniorCreateDto seniorCreateDto) {
		Senior createdSenior = seniorRepository.save(seniorCreateDto.toEntity(account));
		return createdSenior.getId();
	}

	@Override
	@Transactional
	public Long updateSenior(Long seniorId, SeniorUpdateDto seniorUpdateDto) {
		Senior targetSenior = seniorRepository.findById(seniorId)
			.orElseThrow(SeniorNotFoundException::new);

		targetSenior.update(seniorUpdateDto);

		return targetSenior.getId();
	}

	@Override
	public void deleteSenior(Long seniorId) {
		Senior targetSenior = seniorRepository.findById(seniorId)
			.orElseThrow(SeniorNotFoundException::new);

		seniorRepository.delete(targetSenior);
	}

	@Override
	public List<SeniorReadDto> getMySeniors(Account account) {
		List<Senior> seniors = seniorRepository.findAllByAccount(account);
		return SeniorReadDto.fromEntities(seniors);
	}
}
