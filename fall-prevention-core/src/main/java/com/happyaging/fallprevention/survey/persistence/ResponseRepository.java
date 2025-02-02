package com.happyaging.fallprevention.survey.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.survey.entity.reponse.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
