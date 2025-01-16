package com.happyaging.fallprevention.question;

import com.happyaging.fallprevention.product.entity.ProductQuestion;
import com.happyaging.fallprevention.product.service.ProductService;
import com.happyaging.fallprevention.product.usecase.dto.ProductQuestionDto;
import com.happyaging.fallprevention.survey.entity.question.Question;
import com.happyaging.fallprevention.survey.mapper.QuestionMapper;
import com.happyaging.fallprevention.survey.usecase.dto.question.QuestionReorderDto;
import com.happyaging.fallprevention.survey.usecase.dto.question.QuestionRequestDto;
import com.happyaging.fallprevention.survey.usecase.dto.question.QuestionResponseDto;
import com.happyaging.fallprevention.survey.usecase.dto.question.SetNextQuestionDto;
import com.happyaging.fallprevention.survey.usecase.question.QuestionAdminUseCase;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionAdminUseCase questionAdminUseCase;
    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public QuestionResponseDto createQuestion(@Valid @RequestBody QuestionRequestDto questionDto) {
        Question createdQuestion = questionAdminUseCase.createQuestion(questionDto);

        // 질문과 물품 연결
        List<ProductQuestionDto> productQuestionDtoList = questionDto.products();
        if (productQuestionDtoList != null) {
            List<ProductQuestion> productQuestions = new ArrayList<>();
            for (ProductQuestionDto productQuestionDto : productQuestionDtoList) {
                ProductQuestion productQuestion = productService.connectQuestion(
                        productQuestionDto.id(),
                        createdQuestion.getId(),
                        productQuestionDto.priority()
                );
                productQuestions.add(productQuestion);
            }

            return QuestionMapper.toDto(createdQuestion, productQuestions);
        }
        return QuestionMapper.toDto(createdQuestion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResult<QuestionResponseDto>> getQuestion(@PathVariable Long id) {
        Question question = questionAdminUseCase.getQuestionById(id);
        List<ProductQuestion> productQuestions = productService.getAllByQuestionId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, QuestionMapper.toDto(question, productQuestions)));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiSuccessResult<List<QuestionResponseDto>>> getAllQuestions() {
        List<Question> questions = questionAdminUseCase.getAllQuestions();

        List<QuestionResponseDto> questionResponseDtoList = questions.stream()
                .map(question -> {
                    List<ProductQuestion> productQuestions =
                            productService.getAllByQuestionId(question.getId());

                    return QuestionMapper.toDto(question, productQuestions);
                })
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, questionResponseDtoList));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<QuestionResponseDto>> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody  QuestionRequestDto questionDto
    ) {
        Question updatedQuestion = questionAdminUseCase.createQuestion(questionDto);
        questionAdminUseCase.updateQuestion(id, updatedQuestion);

        List<ProductQuestionDto> productQuestionDtoList = questionDto.products();
        if (productQuestionDtoList != null) {
            List<ProductQuestion> productQuestions = new ArrayList<>();
            for (ProductQuestionDto productQuestionDto : productQuestionDtoList) {
                ProductQuestion productQuestion = productService.connectQuestion(
                        productQuestionDto.id(),
                        updatedQuestion.getId(),
                        productQuestionDto.priority()
                );
                productQuestions.add(productQuestion);
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success(HttpStatus.OK, QuestionMapper.toDto(updatedQuestion, productQuestions)));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, QuestionMapper.toDto(updatedQuestion)));
    }

    @DeleteMapping("/{questionId}")
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<Long>> deleteQuestion(@PathVariable Long questionId) {
        Long deletedQuestionId = questionAdminUseCase.deleteAndReorderQuestion(questionId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, deletedQuestionId));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<Boolean>> reorderQuestions(
            @RequestBody List<QuestionReorderDto> reorderList
    ) {
        questionAdminUseCase.reorderQuestions(reorderList);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, true));
    }

    @PutMapping("/next-question")
    @ResponseBody
    public QuestionResponseDto updateNextQuestionOption(@Valid @RequestBody SetNextQuestionDto setNextQuestionDto) {
        Question updatedQuestion = questionAdminUseCase.updateNextQuestionOption(
                setNextQuestionDto.currentQuestionId(),
                setNextQuestionDto.selectedOptionId(),
                setNextQuestionDto.nextQuestionId());

        return QuestionMapper.toDto(updatedQuestion);
    }

}