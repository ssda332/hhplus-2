package hhplus.lectures.presentation.dto;

import lombok.Builder;

@Builder
public record LectureHistResponseDto(
        Long histId,
        Long userId,
        Long lectureId,
        Long optionId
) {
}