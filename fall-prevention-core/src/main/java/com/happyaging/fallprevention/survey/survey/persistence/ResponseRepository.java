package com.happyaging.fallprevention.survey.survey.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.survey.survey.entity.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
