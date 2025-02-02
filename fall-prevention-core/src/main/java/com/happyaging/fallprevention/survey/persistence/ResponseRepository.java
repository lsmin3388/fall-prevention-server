package com.happyaging.fallprevention.survey.persistence;

import com.happyaging.fallprevention.survey.entity.reponse.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
