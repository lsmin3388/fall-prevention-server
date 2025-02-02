package com.happyaging.fallprevention.survey.persistence;

import com.happyaging.fallprevention.survey.entity.question.Question;
import com.happyaging.fallprevention.survey.entity.question.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
    List<QuestionOption> findByNextQuestion(Question nextQuestion);

    @Query("select p from QuestionOption p where p.id in :ids")
    List<QuestionOption> findAllByIds(@Param("ids") List<Long> ids);
}
