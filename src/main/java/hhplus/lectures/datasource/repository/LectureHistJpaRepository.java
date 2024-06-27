package hhplus.lectures.datasource.repository;

import hhplus.lectures.datasource.entity.LectureHistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureHistJpaRepository extends JpaRepository<LectureHistEntity, Long> {
}
