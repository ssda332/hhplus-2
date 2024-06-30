package hhplus.lectures.domain.lecture;

import hhplus.lectures.datasource.entity.LectureEntity;
import hhplus.lectures.datasource.entity.LectureHistEntity;
import hhplus.lectures.datasource.entity.LectureOptionEntity;
import hhplus.lectures.domain.repository.LectureHistRepository;
import hhplus.lectures.domain.repository.LectureRepository;
import hhplus.lectures.domain.service.LectureService;
import hhplus.lectures.exception.*;
import hhplus.lectures.presentation.dto.LectureApplyDto;
import hhplus.lectures.presentation.dto.LectureApplyResponseDto;
import hhplus.lectures.presentation.dto.LectureHistDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {

    @InjectMocks
    private LectureService lectureService;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureHistRepository lectureHistRepository;

    @Test
    @DisplayName("특정 유저ID로 수강 신청하기 성공")
    void applyLectureByUserId() throws AlreadyAppliedException, ExceededLectureException, LectureOptionNotFoundException, LectureNotFoundException {
        // given
        Long userId = 1L;
        Long lectureId = 1L;
        Long optionId = 1L;
        LectureApplyDto dto = LectureApplyDto.builder()
                .userId(userId)
                .lectureId(lectureId)
                .optionId(optionId)
                .build();

        LectureEntity lectureEntity = LectureEntity.builder()
                .lectureId(lectureId)
                .build();

        LectureOptionEntity lectureOptionEntity = LectureOptionEntity.builder()
                .optionId(optionId)
                .lecture(lectureEntity)
                .maxNumber(30L)
                .applyNumber(10L)
                .build();

        List<LectureOptionEntity> list = new ArrayList<>();
        list.add(lectureOptionEntity);

        lectureEntity.setOptions(list);

        LectureHistEntity lectureHistEntity = LectureHistEntity.builder()
                .lectureOption(lectureOptionEntity)
                .userId(userId)
                .build();

        given(lectureRepository.findById(lectureId)).willReturn(Optional.of(lectureEntity));
        given(lectureHistRepository.findByUserIdAndLectureOptionOptionId(userId, optionId)).willReturn(Optional.empty());
        given(lectureRepository.save(any(LectureEntity.class))).willReturn(lectureEntity);
        given(lectureHistRepository.applyLecture(any(LectureHistEntity.class))).willReturn(lectureHistEntity);

        // when
        LectureApplyResponseDto responseDto = lectureService.applyLecture(dto);

        // then
        assertThat(responseDto.userId()).isEqualTo(userId);
        assertThat(responseDto.lectureId()).isEqualTo(lectureId);
        assertThat(responseDto.optionId()).isEqualTo(optionId);
    }

    @Test
    @DisplayName("특강을 찾을 수 없는 경우")
    void applyLectureByUserIdLectureNotFound() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;
        Long optionId = 1L;
        LectureApplyDto dto = LectureApplyDto.builder()
                .userId(userId)
                .lectureId(lectureId)
                .optionId(optionId)
                .build();

        given(lectureRepository.findById(lectureId)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> lectureService.applyLecture(dto))
                .isInstanceOf(LectureNotFoundException.class);
    }

    @Test
    @DisplayName("특강 옵션을 찾을 수 없는 경우")
    void applyLectureByUserIdLectureOptionNotFound() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;
        Long optionId = 1L;
        LectureApplyDto dto = LectureApplyDto.builder()
                .userId(userId)
                .lectureId(lectureId)
                .optionId(optionId)
                .build();

        LectureEntity lectureEntity = LectureEntity.builder()
                .lectureId(lectureId)
                .build();

        LectureOptionEntity lectureOptionEntity = LectureOptionEntity.builder()
                .optionId(2L)
                .lecture(lectureEntity)
                .maxNumber(30L)
                .applyNumber(10L)
                .build();

        List<LectureOptionEntity> list = new ArrayList<>();
        list.add(lectureOptionEntity);
        lectureEntity.setOptions(list);

        given(lectureRepository.findById(lectureId)).willReturn(Optional.of(lectureEntity));

        // when, then
        assertThatThrownBy(() -> lectureService.applyLecture(dto))
                .isInstanceOf(LectureOptionNotFoundException.class);
    }

    @Test
    @DisplayName("이미 신청한 경우")
    void applyLectureByUserIdAlreadyApplied() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;
        Long optionId = 1L;
        LectureApplyDto dto = LectureApplyDto.builder()
                .userId(userId)
                .lectureId(lectureId)
                .optionId(optionId)
                .build();

        LectureEntity lectureEntity = LectureEntity.builder()
                .lectureId(lectureId)
                .build();

        LectureOptionEntity lectureOptionEntity = LectureOptionEntity.builder()
                .optionId(optionId)
                .lecture(lectureEntity)
                .maxNumber(30L)
                .applyNumber(10L)
                .build();

        List<LectureOptionEntity> list = new ArrayList<>();
        list.add(lectureOptionEntity);

        lectureEntity.setOptions(list);

        LectureHistEntity lectureHistEntity = LectureHistEntity.builder()
                .lectureOption(lectureOptionEntity)
                .userId(userId)
                .build();

        given(lectureRepository.findById(lectureId)).willReturn(Optional.of(lectureEntity));
        given(lectureHistRepository.findByUserIdAndLectureOptionOptionId(userId, optionId)).willReturn(Optional.of(lectureHistEntity));

        // when, then
        assertThatThrownBy(() -> lectureService.applyLecture(dto))
                .isInstanceOf(AlreadyAppliedException.class);
    }

    @Test
    @DisplayName("신청 가능 인원을 초과한 경우")
    void applyLectureByUserIdExceededLecture() {
        // given
        Long lectureId = 1L;
        Long optionId = 1L;

        LectureEntity lectureEntity = LectureEntity.builder()
                .lectureId(lectureId)
                .build();

        LectureOptionEntity lectureOptionEntity = LectureOptionEntity.builder()
                .optionId(optionId)
                .lecture(lectureEntity)
                .maxNumber(30L)
                .applyNumber(30L)
                .build();

        List<LectureOptionEntity> list = new ArrayList<>();
        list.add(lectureOptionEntity);

        lectureEntity.setOptions(list);

        // when, then
        assertThatThrownBy(() -> {
            lectureOptionEntity.enroll();
        }).isInstanceOf(ExceededLectureException.class);
    }

    @Test
    @DisplayName("수강 신청 내역이 없는 경우")
    void getLectureHistNotFound() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;
        Long optionId = 1L;
        LectureHistDto dto = LectureHistDto.builder()
                .userId(userId)
                .lectureId(lectureId)
                .optionId(optionId)
                .build();

        given(lectureHistRepository.findByUserIdAndLectureOptionOptionId(userId, optionId)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> lectureService.getLectureHist(dto))
                .isInstanceOf(LectureHistNotFoundException.class);
    }
}
