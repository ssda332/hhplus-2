package hhplus.lectures.datasource.repository;

import hhplus.lectures.datasource.entity.LectureHistEntity;
import hhplus.lectures.domain.repository.LectureHistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureHistRepositoryImpl implements LectureHistRepository {

    private final LectureHistJpaRepository lectureHistJpaRepository;

    @Override
    public LectureHistEntity applyLecture(LectureHistEntity histEntity) {
        return lectureHistJpaRepository.save(histEntity);
    }

    @Override
    public Optional<LectureHistEntity> findByUserIdAndLectureOptionOptionId(Long userId, Long optionId) {
        return lectureHistJpaRepository.findByUserIdAndLectureOptionOptionId(userId, optionId);
    }

}
