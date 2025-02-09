package survey.usecase.dto.question;

import java.util.List;

import com.happyaging.fallprevention.product.usecase.dto.ProductQuestionDto;

import lombok.Builder;
import survey.entity.question.QuestionCategory;
import survey.entity.question.QuestionType;

@Builder
public record QuestionResponseDto(
         Long id,
         Integer order,
         String content,
         String imageUrl,
         List<QuestionOptionResponseDto> options,
         QuestionCategory questionCategory,
         QuestionType questionType,
         List<ProductQuestionDto> products
) {
}
