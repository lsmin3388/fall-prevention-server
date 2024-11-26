package com.happyaging.fallprevention.senior.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.happyaging.fallprevention.senior.entity.Senior;

public interface SeniorRepository extends JpaRepository<Senior, Long> {
}
