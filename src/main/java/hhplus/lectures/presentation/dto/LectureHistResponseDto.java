package hhplus.lectures.presentation.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record LectureHistResponseDto(
        Long histId,
        Long userId,
        Long lectureId,
        Long optionId
) {
}