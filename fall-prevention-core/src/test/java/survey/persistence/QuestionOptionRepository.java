package survey.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import survey.entity.question.Question;
import survey.entity.question.QuestionOption;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
    List<QuestionOption> findByNextQuestion(Question nextQuestion);

    @Query("select p from Option p where p.id in :ids")
    List<QuestionOption> findAllByIds(@Param("ids") List<Long> ids);
}
