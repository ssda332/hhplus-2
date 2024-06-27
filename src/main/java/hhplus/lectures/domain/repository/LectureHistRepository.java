package hhplus.lectures.domain.repository;

import hhplus.lectures.datasource.entity.LectureHistEntity;
import hhplus.lectures.presentation.dto.LectureApplyDto;

public interface LectureHistRepository {
    LectureHistEntity applyLecture(LectureApplyDto dto);
}
