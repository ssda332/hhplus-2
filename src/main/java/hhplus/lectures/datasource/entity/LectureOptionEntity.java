package hhplus.lectures.datasource.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LECTURE_OPTION")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LectureOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private LectureEntity lecture;

    private Long maxNumber;
    private Long applyNumber;
}