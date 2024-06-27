package hhplus.lectures.datasource.repository;

import hhplus.lectures.datasource.entity.LectureHistEntity;
import hhplus.lectures.domain.repository.LectureHistRepository;
import hhplus.lectures.presentation.dto.LectureApplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LectureHistRepositoryImpl implements LectureHistRepository {

    private LectureHistJpaRepository lectureHistJpaRepository;

    @Override
    public LectureHistEntity applyLecture(LectureApplyDto dto) {
        return lectureHistJpaRepository.save(null);
    }
}
