package com.happyaging.fallprevention.region.service;

import org.springframework.stereotype.Service;

import com.happyaging.fallprevention.region.entity.Region;
import com.happyaging.fallprevention.region.exception.RegionNotFoundException;
import com.happyaging.fallprevention.region.persistence.RegionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {
	private final RegionRepository regionRepository;

	public Region getRegionByRegionName(String regionName) {
		return regionRepository.findByRegionName(regionName)
			.orElseThrow(RegionNotFoundException::new);
	}
}
