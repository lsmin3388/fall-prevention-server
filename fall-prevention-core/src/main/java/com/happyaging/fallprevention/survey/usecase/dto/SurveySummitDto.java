package com.happyaging.fallprevention.survey.usecase.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SurveySummitDto(
    @NotNull(message = "어르신을 찾을수 없습니다.")
    Long seniorId,
    List<ResponseSummitDto> responseSummitDtoList
) {
}
