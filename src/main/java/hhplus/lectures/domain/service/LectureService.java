package hhplus.lectures.domain.service;

import hhplus.lectures.datasource.entity.LectureEntity;
import hhplus.lectures.datasource.entity.LectureHistEntity;
import hhplus.lectures.datasource.entity.LectureOptionEntity;
import hhplus.lectures.domain.repository.LectureHistRepository;
import hhplus.lectures.domain.repository.LectureRepository;
import hhplus.lectures.exception.*;
import hhplus.lectures.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureHistRepository lectureHistRepository;
    private final LectureRepository lectureRepository;

    public LectureApplyResponseDto applyLecture(LectureApplyDto dto) throws LectureNotFoundException, LectureOptionNotFoundException, ExceededLectureException, AlreadyAppliedException {

        Long optionId = dto.optionId();
        Long lectureId = dto.lectureId();
        Long userId = dto.userId();

        // 특강 찾기
        LectureEntity lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureNotFoundException());

        // 특강 신청하기
        LectureOptionEntity option = lecture.getOption(optionId);

        // 이미 신청했는지 여부 확인
        boolean present = lectureHistRepository.findByUserIdAndLectureOptionOptionId(userId, optionId)
                .isPresent();

        if (present) {
            throw new AlreadyAppliedException();
        }

        // 신청 처리
        option.enroll();

        LectureEntity lectureEntity = lectureRepository.save(lecture);

        // 특강 신청내역 저장하기
        LectureHistEntity histEntity = LectureHistEntity.builder()
                .lectureOption(option)
                .userId(dto.userId())
                .build();

        LectureHistEntity lectureHistEntity = lectureHistRepository.applyLecture(histEntity);

        LectureApplyResponseDto responseDto = LectureApplyResponseDto.builder()
                .userId(lectureHistEntity.getUserId())
                .lectureId(lectureEntity.getLectureId())
                .optionId(option.getOptionId())
                .build();

        return responseDto;
    }

    public List<LectureResponseDto> selectLectureList() {
        return lectureRepository.findAll().stream()
                .map(LectureEntity::toDto)
                .collect(Collectors.toList());
    }

    public LectureHistResponseDto getLectureHist(LectureHistDto dto) throws LectureHistNotFoundException, LectureNotFoundException {
        LectureHistEntity lectureHistEntity = lectureHistRepository.findByUserIdAndLectureOptionOptionId(dto.userId(), dto.optionId())
                .orElseThrow(() -> new LectureHistNotFoundException());

        return lectureHistEntity.toLectureHistResponseDto();
    }
}
