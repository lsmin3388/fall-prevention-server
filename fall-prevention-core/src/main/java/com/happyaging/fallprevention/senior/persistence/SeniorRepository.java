package com.happyaging.fallprevention.senior.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.senior.entity.Senior;

public interface SeniorRepository extends JpaRepository<Senior, Long> {
}
