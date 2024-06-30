package hhplus.lectures.datasource.repository;

import hhplus.lectures.datasource.entity.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<LectureEntity, Long> {
}
