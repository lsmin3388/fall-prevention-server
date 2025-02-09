package survey.mapper;

import java.util.List;

import com.happyaging.fallprevention.product.mapper.ProductQuestionMapper;

import survey.entity.question.Question;
import survey.entity.question.QuestionOption;
import survey.usecase.dto.question.QuestionOptionResponseDto;
import survey.usecase.dto.question.QuestionResponseDto;

public class QuestionMapper {
    public static QuestionResponseDto toDto(Question question) {
        return QuestionResponseDto.builder()
                .id(question.getId())
                .order(question.getOrderNumber())
                .options(question.getOptions().stream().map(QuestionMapper::toDto).toList())
                .content(question.getContent())
                .imageUrl(question.getImageUrl())
                .questionCategory(question.getCategory())
                .questionType(question.getType())
                .build();
    }

    public static QuestionResponseDto toDto(Question question, List<ProductQuestion> productQuestions) {
        return QuestionResponseDto.builder()
                .id(question.getId())
                .order(question.getOrderNumber())
                .options(question.getOptions().stream().map(QuestionMapper::toDto).toList())
                .content(question.getContent())
                .imageUrl(question.getImageUrl())
                .questionCategory(question.getCategory())
                .questionType(question.getType())
                .products(productQuestions.stream().map(ProductQuestionMapper::toDto).toList())
                .build();
    }

    public static QuestionOptionResponseDto toDto(QuestionOption questionOption) {
        return QuestionOptionResponseDto.builder()
                .id(questionOption.getId())
                .order(questionOption.getOrderNumber())
                .content(questionOption.getContent())
                .nextQuestionId(questionOption.getNextQuestion() != null ? questionOption.getNextQuestion().getId() : null)
                .build();
    }
}
