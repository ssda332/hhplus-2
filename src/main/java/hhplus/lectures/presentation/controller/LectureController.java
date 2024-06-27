package hhplus.lectures.presentation.controller;

import hhplus.lectures.exception.AlreadyAppliedException;
import hhplus.lectures.exception.ExceededLectureException;
import hhplus.lectures.exception.LectureNotFoundException;
import hhplus.lectures.exception.LectureOptionNotFoundException;
import hhplus.lectures.presentation.dto.LectureApplyDto;
import hhplus.lectures.presentation.dto.LectureResponseDto;
import hhplus.lectures.domain.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @PostMapping("/apply")
    public ResponseEntity<LectureResponseDto> applyLecture(@RequestBody LectureApplyDto dto) throws LectureNotFoundException, LectureOptionNotFoundException, ExceededLectureException, AlreadyAppliedException {
        return ResponseEntity.ok(lectureService.applyLecture(dto));
    }

    @GetMapping("/")
    public ResponseEntity<String> selectLecture(){

        return ResponseEntity.ok(null);
    }
}
