package survey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import survey.entity.question.Question;
import survey.entity.question.QuestionOption;
import survey.entity.reponse.Response;
import survey.entity.reponse.ResponseOption;
import survey.exception.QuestionOptionNotFoundException;
import survey.exception.ResponseNotFoundException;
import survey.persistence.QuestionOptionRepository;
import survey.persistence.ResponseOptionRepository;
import survey.persistence.ResponseRepository;
import survey.usecase.ResponseUseCase;
import survey.usecase.dto.ResponseSummitDto;

@Service
@RequiredArgsConstructor
public class ResponseService implements ResponseUseCase {

    private final ResponseRepository responseRepository;
    private final QuestionAdminService questionAdminService;
    private final QuestionOptionRepository questionOptionRepository;
    private final ResponseOptionRepository responseOptionRepository;

    @Override
    @Transactional
    public Response createResponse(Response response) {
        return responseRepository.save(response);
    }

    @Override
    public Response getResponseById(Long id) {
        return responseRepository.findById(id).orElseThrow(ResponseNotFoundException::new);
    }

    @Override
    public List<Response> getAllResponses() {
        return responseRepository.findAll();
    }

    @Override
    public Response getResponseByQuestion(List<Response> responses, Question question) {
        for (Response response : responses) {
            if (response.getQues11tion().getId().equals(question.getId())) {
                return response;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public List<Response> convertAndSaveAll(List<ResponseSummitDto> responseSummitDtoList) {
        List<Response> responses = new ArrayList<>();

        for (ResponseSummitDto responseSummitDto : responseSummitDtoList) {
            Question question = questionAdminService.getQuestionById(responseSummitDto.questionId());
            Response response = responseSummitDto.toEntity(question);

            responseRepository.save(response);

            List<Long> questionOptionId = responseSummitDto.questionOptionIds();
            if (!questionOptionId.isEmpty()) {
                List<QuestionOption> questionOptions = questionOptionRepository.findAllByIds(questionOptionId);
                if (questionOptions.isEmpty()) {
                    throw new QuestionOptionNotFoundException();
                }

                List<ResponseOption> responseOptions = new ArrayList<>();
                for (QuestionOption questionOption : questionOptions) {
                    ResponseOption responseOption = ResponseOption.builder()
                            .response(response)
                            .questionOption(questionOption).build();

                    responseOptions.add(responseOption);
                    responseOptionRepository.save(responseOption);
                }
                response.setResponseOptions(responseOptions);
            } else {
                response.setAnswerText(responseSummitDto.answerText());
            }

            responses.add(response);
            responseRepository.save(response);
        }

        return responses;

    }
}
