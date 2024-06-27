package hhplus.lectures.presentation.dto;

import lombok.Builder;

@Builder
public record LectureOptionDto(
        Long optionId,
        Long maxNumber,
        Long applyNumber
) {
}
