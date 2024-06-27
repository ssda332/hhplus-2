package hhplus.lectures.domain.repository;

import hhplus.lectures.datasource.entity.LectureHistEntity;
import hhplus.lectures.presentation.dto.LectureApplyDto;

import java.util.List;
import java.util.Optional;

public interface LectureHistRepository {
    LectureHistEntity applyLecture(LectureHistEntity histEntity);
    Optional<LectureHistEntity> findByUserIdAndLectureOptionOptionId(Long userId, Long optionId);

}
