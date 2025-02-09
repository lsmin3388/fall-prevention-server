package survey.usecase.dto;

import java.util.List;

import survey.entity.question.Question;
import survey.entity.reponse.Response;

public record ResponseSummitDto(
        Long questionId,
        List<Long> questionOptionIds,
        String answerText
) {
    public Response toEntity(Question question) {
        return Response.builder()
                .question(question)
                .answerText(answerText)
                .build();
    }
}
