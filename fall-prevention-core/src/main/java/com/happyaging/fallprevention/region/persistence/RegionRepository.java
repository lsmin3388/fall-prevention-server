package com.happyaging.fallprevention.region.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.region.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
