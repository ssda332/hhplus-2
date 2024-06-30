package hhplus.lectures.presentation.dto;

import lombok.Builder;

@Builder
public record LectureHistDto(
        Long userId,
        Long lectureId,
        Long optionId
) {

}
