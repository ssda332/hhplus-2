package hhplus.lectures.datasource.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LECTURE_HIST")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LectureHistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long histId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private LectureOptionEntity lectureOption;

    private Long userId;
}