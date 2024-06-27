package hhplus.lectures.datasource.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "LECTURE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LectureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    // LectureOptionEntity에도 영속성 작업 적용하고 lecture 지우면 option도 지워짐
    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LectureOptionEntity> options;
}