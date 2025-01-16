package com.happyaging.fallprevention.survey.persistence;

import com.happyaging.fallprevention.survey.entity.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Soft Delete가 적용된 질문 제외
    @Query("SELECT q FROM Question q WHERE q.isDeleted = false")
    List<Question> findNonDeletedQuestions();

    // 현재 질문 번호의 최댓값
    @Query("SELECT COALESCE(MAX(q.orderNumber), 0) FROM Question q")
    Integer findMaxOrderNumber();

    // 특정 순서보다 큰 질문 조회
    @Query("SELECT q FROM Question q WHERE q.orderNumber > :orderNumber")
    List<Question> findByOrderNumberGreaterThan(Integer orderNumber);


}
