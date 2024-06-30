package hhplus.lectures.presentation.dto;

import lombok.Builder;

@Builder
public record LectureApplyResponseDto(
        Long lectureId,
        Long optionId,
        Long userId
) {
}
