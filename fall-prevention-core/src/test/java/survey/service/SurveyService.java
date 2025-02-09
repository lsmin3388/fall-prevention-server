package survey.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import survey.persistence.SurveyRepository;
import survey.usecase.SurveyUserUseCase;
import survey.usecase.dto.SurveyReadDto;
import survey.usecase.dto.SurveySummitDto;

@Service
@RequiredArgsConstructor
public class SurveyService implements SurveyUserUseCase {
    private final SurveyRepository surveyRepository;


    @Override
    @Transactional
    public Long createSurvey(SurveySummitDto surveyCreateDto) {
        return 0L;
    }

    @Override
    public List<SurveyReadDto> getSurveysBySeniorId(Long seniorId) {
        return List.of();
    }

    @Override
    public SurveyReadDto getSurveyById(Long surveyId) {
        return null;
    }

    @Override
    @Transactional
    public Long deleteSurvey(Long surveyId, Long seniorId) {
        return 0L;
    }
}
