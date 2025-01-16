package com.happyaging.fallprevention.survey.mapper;

import com.happyaging.fallprevention.product.entity.ProductQuestion;
import com.happyaging.fallprevention.product.mapper.ProductQuestionMapper;
import com.happyaging.fallprevention.survey.entity.question.Question;
import com.happyaging.fallprevention.survey.entity.question.QuestionOption;
import com.happyaging.fallprevention.survey.usecase.dto.question.QuestionOptionResponseDto;
import com.happyaging.fallprevention.survey.usecase.dto.question.QuestionResponseDto;

import java.util.List;

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
