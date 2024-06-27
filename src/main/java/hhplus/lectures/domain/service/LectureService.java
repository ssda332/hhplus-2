package hhplus.lectures.domain.service;

import hhplus.lectures.datasource.entity.LectureEntity;
import hhplus.lectures.datasource.entity.LectureHistEntity;
import hhplus.lectures.datasource.entity.LectureOptionEntity;
import hhplus.lectures.domain.repository.LectureHistRepository;
import hhplus.lectures.domain.repository.LectureRepository;
import hhplus.lectures.exception.AlreadyAppliedException;
import hhplus.lectures.exception.ExceededLectureException;
import hhplus.lectures.exception.LectureNotFoundException;
import hhplus.lectures.exception.LectureOptionNotFoundException;
import hhplus.lectures.presentation.dto.LectureApplyDto;
import hhplus.lectures.presentation.dto.LectureResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureHistRepository lectureHistRepository;
    private final LectureRepository lectureRepository;

    public LectureResponseDto applyLecture(LectureApplyDto dto) throws LectureNotFoundException, LectureOptionNotFoundException, ExceededLectureException, AlreadyAppliedException {

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

        LectureResponseDto responseDto = LectureResponseDto.builder()
                .userId(lectureHistEntity.getUserId())
                .lectureId(lectureEntity.getLectureId())
                .optionId(option.getOptionId())
                .build();

        return responseDto;
    }
}
