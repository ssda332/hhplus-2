package hhplus.lectures.presentation.dto;

import lombok.Builder;

@Builder
public record LectureResponseDto(
        Long lectureId,
        Long optionId,
        Long userId
) {
}
