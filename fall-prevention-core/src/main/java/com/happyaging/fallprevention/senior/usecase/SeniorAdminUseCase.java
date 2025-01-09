package com.happyaging.fallprevention.senior.usecase;

import java.util.List;

import com.happyaging.fallprevention.senior.usecase.dto.SeniorReadDto;

public interface SeniorAdminUseCase {
	List<SeniorReadDto> getSeniors();
}
