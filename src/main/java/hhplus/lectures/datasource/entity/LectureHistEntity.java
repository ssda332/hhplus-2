package hhplus.lectures.datasource.entity;

import hhplus.lectures.presentation.dto.LectureHistResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.stream.Collectors;

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

    public LectureHistResponseDto toLectureHistResponseDto() {
        return LectureHistResponseDto.builder()
                .histId(this.histId)
                .userId(this.userId)
                .lectureId(this.lectureOption.getLecture().getLectureId())
                .optionList(this.lectureOption.getLecture().getOptions().stream()
                        .map(LectureOptionEntity::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}