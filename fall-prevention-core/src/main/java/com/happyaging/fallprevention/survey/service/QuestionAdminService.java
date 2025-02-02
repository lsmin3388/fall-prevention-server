package com.happyaging.fallprevention.survey.service;

import com.happyaging.fallprevention.survey.entity.question.Question;
import com.happyaging.fallprevention.survey.entity.question.QuestionOption;
import com.happyaging.fallprevention.survey.exception.QuestionNotFoundException;
import com.happyaging.fallprevention.survey.exception.QuestionOptionNotFoundException;
import com.happyaging.fallprevention.survey.persistence.QuestionOptionRepository;
import com.happyaging.fallprevention.survey.persistence.QuestionRepository;
import com.happyaging.fallprevention.survey.usecase.dto.question.QuestionRequestDto;
import com.happyaging.fallprevention.survey.usecase.dto.question.QuestionReorderDto;
import com.happyaging.fallprevention.survey.usecase.question.QuestionAdminUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionAdminService implements QuestionAdminUseCase {
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;

    @Override
    @Transactional
    public Question createQuestion(QuestionRequestDto questionDto) {
        Integer maxOrderNumber = questionRepository.findMaxOrderNumber();
        Question question = questionDto.toEntity();
        question.setOrderNumber(maxOrderNumber + 1);
        // Set options after building the Question
        if (questionDto.options() != null) {
            question.setOptions(questionDto.options().stream()
                    .map(optionDto -> QuestionOption.builder()
                            .orderNumber(optionDto.orderNumber())
                            .content(optionDto.content())
                            .question(question)
                            .nextQuestion(optionDto.nextQuestionId() != null ? getQuestionById(optionDto.nextQuestionId()) : null)
                            .build()
                    )
                    .toList());
        }

        return questionRepository.save(question);
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findNonDeletedQuestions();
    }

    @Override
    @Transactional
    public void updateQuestion(Long questionId, Question question) {
        Question existingQuestion = getQuestionById(questionId);
        existingQuestion.update(question);
        questionRepository.save(existingQuestion);
    }

    @Override
    @Transactional
    public Long deleteAndReorderQuestion(Long questionId) {
        Question questionToDelete = getQuestionById(questionId);
        List<QuestionOption> options = questionOptionRepository.findByNextQuestion(questionToDelete);
        for (QuestionOption option : options) {
            option.setNextQuestion(null);
        }
        Integer orderNumber = questionToDelete.getOrderNumber();
        questionToDelete.setOrderNumber(null);
        questionRepository.save(questionToDelete);
        questionRepository.delete(questionToDelete);

        // Fetch and update the order numbers of the remaining questions
        List<Question> questionsToUpdate = questionRepository.findByOrderNumberGreaterThan(orderNumber);
        for (Question question  : questionsToUpdate) {
            question.setOrderNumber(question.getOrderNumber() - 1);
        }

        // Save the updated questions
        questionRepository.saveAll(questionsToUpdate);

        return questionId;
    }

    @Override
    @Transactional
    public void reorderQuestions(List<QuestionReorderDto> reorderList) {
        List<Long> questionIds = reorderList.stream()
                .map(QuestionReorderDto::getId)
                .toList();
        List<Question> questions = questionRepository.findAllById(questionIds);

        for (Question question : questions) {
            question.setOrderNumber(null);
        }
        questionRepository.saveAll(questions);

        Map<Long, Integer> newOrderMap = reorderList.stream()
                .collect(Collectors.toMap(QuestionReorderDto::getId, QuestionReorderDto::getOrder));

        for (Question question : questions) {
            Integer newOrder = newOrderMap.get(question.getId());
            if (newOrder != null) {
                question.setOrderNumber(newOrder);
            }
        }

        questionRepository.saveAll(questions);
    }

    @Override
    @Transactional
    public Question updateNextQuestionOption(Long currentQuestionId, Long questionOptionId, Long nextQuestionId) {
        Question currentQuestion = getQuestionById(currentQuestionId);
        QuestionOption selectedOption = questionOptionRepository.findById(questionOptionId).orElseThrow(QuestionOptionNotFoundException::new);

        Question nextQuestion = getQuestionById(nextQuestionId);

        selectedOption.setNextQuestion(nextQuestion);
        questionOptionRepository.save(selectedOption);

        return currentQuestion;
    }


}
