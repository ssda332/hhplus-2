package hhplus.lectures.datasource.repository;

import hhplus.lectures.datasource.entity.LectureEntity;
import hhplus.lectures.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Optional<LectureEntity> findById(Long lectureId) {
        return lectureJpaRepository.findById(lectureId);
    }

    @Override
    public LectureEntity save(LectureEntity lecture) {
        return lectureJpaRepository.save(lecture);
    }

    @Override
    public List<LectureEntity> findAll() {
        return lectureJpaRepository.findAll();
    }
}
