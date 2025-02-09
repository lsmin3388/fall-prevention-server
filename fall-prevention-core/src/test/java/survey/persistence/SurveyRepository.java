package survey.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import survey.entity.survey.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findAllBySeniorId(Long seniorId);
}
