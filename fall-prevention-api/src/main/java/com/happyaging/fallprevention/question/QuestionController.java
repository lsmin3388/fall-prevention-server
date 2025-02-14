package com.happyaging.fallprevention.question;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.survey.question.usecase.QuestionUseCase;
import com.happyaging.fallprevention.survey.question.usecase.dto.request.QuestionSaveDto;
import com.happyaging.fallprevention.survey.question.usecase.dto.response.QuestionReadDto;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionUseCase questionUseCase;

    /**
     * 질문 생성 - ADMIN 전용
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<Integer>> createQuestion(
        @Valid @RequestBody QuestionSaveDto questionSaveDto) {
        Integer questionNumber = questionUseCase.createQuestion(questionSaveDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED, questionNumber));
    }

    /**
     * 질문 수정 - ADMIN 전용
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<Integer>> updateQuestion(
        @PathVariable("id") Integer id,
        @Valid @RequestBody QuestionSaveDto questionSaveDto) {
        Integer questionNumber = questionUseCase.updateQuestion(id, questionSaveDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(HttpStatus.OK, questionNumber));
    }

    /**
     * 질문 삭제 - ADMIN 전용
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<String>> deleteQuestion(@PathVariable("id") Integer id) {
        questionUseCase.deleteQuestion(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(HttpStatus.OK, "Question deleted successfully"));
    }


    /**
     * 질문 조회 - USER 전용 (ADMIN도 포함)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<QuestionReadDto>> getQuestion(@PathVariable("id") Integer id) {
        QuestionReadDto questions = questionUseCase.getQuestion(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(HttpStatus.OK, questions));
    }

    /**
     * 전체 질문 조회 - USER 전용 (ADMIN도 포함)
     */
    @GetMapping
    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<List<QuestionReadDto>>> getAllQuestions() {
        List<QuestionReadDto> questions = questionUseCase.getAllQuestions();
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(HttpStatus.OK, questions));
    }
}
