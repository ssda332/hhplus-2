package hhplus.lectures.datasource.entity;

import hhplus.lectures.exception.ExceededLectureException;
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

    // 신청 메소드 추가
    public void enroll() throws ExceededLectureException {
        if (applyNumber >= maxNumber) {
            throw new ExceededLectureException();
        }
        applyNumber++;
    }
}