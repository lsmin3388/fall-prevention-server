package com.happyaging.fallprevention.survey.persistence;

import com.happyaging.fallprevention.survey.entity.reponse.ResponseOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseOptionRepository extends JpaRepository<ResponseOption, Long> {
}
