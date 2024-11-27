package com.happyaging.fallprevention.region.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.region.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
	Optional<Region> findByRegionName(String regionName);
}
