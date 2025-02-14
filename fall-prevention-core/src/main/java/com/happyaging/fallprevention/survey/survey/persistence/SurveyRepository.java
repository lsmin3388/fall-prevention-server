package com.happyaging.fallprevention.survey.survey.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.happyaging.fallprevention.survey.survey.entity.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

	/**
	 * 단일 Survey를 Response와 Option까지 한 번에 로딩
	 * (N+1 방지)
	 */
	@Query("""
        select distinct s
        from Survey s
        left join fetch s.responses r
        left join fetch r.options ro
        left join fetch ro.option opt
        left join fetch opt.nextQuestion nextQ
        left join fetch r.question q
        where s.id = :surveyId
        """)
	Optional<Survey> findSurveyByIdWithAllRelations(@Param("surveyId") Long surveyId);

	/**
	 * 전체 Survey 목록을 Response와 Option까지 한 번에 로딩
	 * (N+1 방지)
	 */
	@Query("""
        select distinct s
        from Survey s
        left join fetch s.responses r
        left join fetch r.options ro
        left join fetch ro.option opt
        left join fetch opt.nextQuestion nextQ
        left join fetch r.question q
        """)
	List<Survey> findAllWithAllRelations();
}
