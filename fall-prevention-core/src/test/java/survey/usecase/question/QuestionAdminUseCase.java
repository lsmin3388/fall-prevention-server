package survey.usecase.question;

import java.util.List;

import survey.entity.question.Question;
import survey.usecase.dto.question.QuestionReorderDto;
import survey.usecase.dto.question.QuestionRequestDto;

public interface QuestionAdminUseCase {
    Question createQuestion(QuestionRequestDto questionDto);
    Question getQuestionById(Long id);
    List<Question> getAllQuestions();
    void updateQuestion(Long questionId, Question question);
    Long deleteAndReorderQuestion(Long questionId);
    void reorderQuestions(List<QuestionReorderDto> reorderList);
    Question updateNextQuestionOption(Long currentQuestionId, Long questionOptionId, Long nextQuestionId);
}
