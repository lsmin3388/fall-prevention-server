package survey.usecase.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record SurveySummitDto(
    @NotNull(message = "어르신을 찾을수 없습니다.")
    Long seniorId,
    List<ResponseSummitDto> responseSummitDtoList
) {
}
