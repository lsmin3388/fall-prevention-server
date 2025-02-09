package survey.usecase.dto.question;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionReorderDto {
    private Long id;
    private Integer order;
}
