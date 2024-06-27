package hhplus.lectures.datasource.repository;

import hhplus.lectures.datasource.entity.LectureHistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureHistJpaRepository extends JpaRepository<LectureHistEntity, Long> {
    Optional<LectureHistEntity> findByUserIdAndLectureOptionOptionId(Long userId, Long optionId);
}
