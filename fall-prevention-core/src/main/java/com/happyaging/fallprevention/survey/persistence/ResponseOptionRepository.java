package com.happyaging.fallprevention.survey.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.survey.entity.reponse.ResponseOption;

public interface ResponseOptionRepository extends JpaRepository<ResponseOption, Long> {
}
