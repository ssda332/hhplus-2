package hhplus.lectures.datasource.repository;

import hhplus.lectures.datasource.entity.LectureEntity;
import hhplus.lectures.domain.repository.LectureRepository;
import hhplus.lectures.presentation.dto.LectureResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private LectureJpaRepository lectureJpaRepository;

    @Override
    public Optional<LectureEntity> findById(Long lectureId) {
        return lectureJpaRepository.findById(lectureId);
    }
}
