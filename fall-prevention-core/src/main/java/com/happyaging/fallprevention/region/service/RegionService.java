package com.happyaging.fallprevention.region.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import com.happyaging.fallprevention.region.entity.Region;
import com.happyaging.fallprevention.region.exception.RegionNotFoundException;
import com.happyaging.fallprevention.region.persistence.RegionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {
	private final RegionRepository regionRepository;

	public Region getRegionByRegionName(String regionName) {
		return regionRepository.findByRegionName(regionName)
			.orElseThrow(RegionNotFoundException::new);
	}

	@Transactional
	public void createRegion(@Valid Region region) {
		regionRepository.save(region);
	}

	@Transactional
	public void updateRegion(Long regionId, String regionName) {
		Region region = regionRepository.findById(regionId)
				.orElseThrow(RegionNotFoundException::new);
		region.updateRegionName(regionName);
	}


	@Transactional
	public void deleteRegion(@Valid Region region) {
		regionRepository.delete(region);
	}

	public List<Region> getAllRegions() {
		return regionRepository.findAll();
	}
}
