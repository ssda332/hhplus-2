package hhplus.lectures.presentation.dto;

import lombok.Builder;

@Builder
public record LectureHistDto(
        Long histId,
        Long userId,
        Long lectureId
) {

}
