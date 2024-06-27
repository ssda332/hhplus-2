package hhplus.lectures.datasource.entity;

import hhplus.lectures.exception.LectureOptionNotFoundException;
import hhplus.lectures.presentation.dto.LectureResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

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

    public LectureOptionEntity getOption(Long optionId) throws LectureOptionNotFoundException {
        LectureOptionEntity result = null;

        for (LectureOptionEntity option : options) {
            if (option.getOptionId() == optionId) {
                result = option;
            }
        }

        if (result == null) throw new LectureOptionNotFoundException();
        return result;
    }

    public LectureResponseDto toDto() {
        return LectureResponseDto.builder()
                .lectureId(this.lectureId)
                .optionList(this.options.stream()
                        .map(LectureOptionEntity::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}