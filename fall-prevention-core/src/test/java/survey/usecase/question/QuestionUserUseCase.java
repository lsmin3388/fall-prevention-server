package survey.usecase.question;

import java.util.List;

import survey.entity.question.Question;

public interface QuestionUserUseCase {
    // Read
    Question getQuestionById(Long id);
    List<Question> getAllQuestions();
}
