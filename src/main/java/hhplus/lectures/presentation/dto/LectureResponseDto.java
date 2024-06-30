package hhplus.lectures.presentation.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record LectureResponseDto(
        Long lectureId,
        List<LectureOptionDto> optionList
) {
}
