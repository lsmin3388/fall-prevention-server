package survey.usecase;

import java.util.List;

import survey.usecase.dto.SurveyReadDto;
import survey.usecase.dto.SurveySummitDto;

public interface SurveyUserUseCase {
    Long createSurvey(SurveySummitDto surveyCreateDto);

    List<SurveyReadDto> getSurveysBySeniorId(Long seniorId);

    SurveyReadDto getSurveyById(Long surveyId);

    Long deleteSurvey(Long surveyId, Long seniorId);
}
