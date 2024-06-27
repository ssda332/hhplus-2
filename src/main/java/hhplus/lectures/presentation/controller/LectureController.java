package hhplus.lectures.presentation.controller;

import hhplus.lectures.exception.AlreadyAppliedException;
import hhplus.lectures.exception.ExceededLectureException;
import hhplus.lectures.exception.LectureNotFoundException;
import hhplus.lectures.exception.LectureOptionNotFoundException;
import hhplus.lectures.presentation.dto.LectureApplyDto;
import hhplus.lectures.presentation.dto.LectureApplyResponseDto;
import hhplus.lectures.domain.service.LectureService;
import hhplus.lectures.presentation.dto.LectureResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @PostMapping("/apply")
    public ResponseEntity<LectureApplyResponseDto> applyLecture(@RequestBody LectureApplyDto dto) throws LectureNotFoundException, LectureOptionNotFoundException, ExceededLectureException, AlreadyAppliedException {
        return ResponseEntity.ok(lectureService.applyLecture(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<LectureResponseDto>> selectLectureList(){
        return ResponseEntity.ok(lectureService.selectLectureList());
    }
}
