package hhplus.lectures.domain.repository;

import hhplus.lectures.datasource.entity.LectureEntity;

import java.util.Optional;

public interface LectureRepository {
    Optional<LectureEntity> findById(Long lectureId);
    LectureEntity save(LectureEntity lecture);
}
