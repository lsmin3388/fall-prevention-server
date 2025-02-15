package com.happyaging.fallprevention.survey.survey.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.happyaging.fallprevention.survey.survey.entity.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

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

	// [추가] seniorId 기준으로 fetch join하여 설문 목록을 조회 (메모리 필터링 대신 DB 단계에서 필터링)
	@Query("""
        select distinct s
        from Survey s
        left join fetch s.responses r
        left join fetch r.options ro
        left join fetch ro.option opt
        left join fetch opt.nextQuestion nextQ
        left join fetch r.question q
        where s.senior.id = :seniorId
        """)
	List<Survey> findAllBySeniorIdWithAllRelations(@Param("seniorId") Long seniorId);
}
