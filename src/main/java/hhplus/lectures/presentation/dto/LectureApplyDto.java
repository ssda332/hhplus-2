package hhplus.lectures.presentation.dto;

import lombok.Builder;

@Builder
public record LectureApplyDto(
        long userId,
        long lectureId,
        long optionId
) {

}
