package survey.usecase.dto.question;

import jakarta.validation.constraints.NotBlank;
import survey.entity.question.Question;
import survey.entity.question.QuestionOption;

public record QuestionOptionRequestDto(
        @NotBlank(message = "선택지 순서를 입력하세요.")
        Integer orderNumber,
        @NotBlank(message = "선택지 내용을 입력하세요.")
        String content,
        Long nextQuestionId
) {
        public QuestionOption toEntity(Question question ,Question nextQuestion) {
                return QuestionOption.builder()
                        .orderNumber(orderNumber)
                        .content(content)
                        .nextQuestion(nextQuestion)
                        .build();
        }

}
