package hhplus.lectures.presentation.controller;

import hhplus.lectures.exception.*;
import hhplus.lectures.presentation.dto.*;
import hhplus.lectures.domain.service.LectureService;
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

    @GetMapping("/application")
    public ResponseEntity<LectureHistResponseDto> getLectureHist(@RequestBody LectureHistDto dto) throws LectureHistNotFoundException, LectureNotFoundException {
        return ResponseEntity.ok(lectureService.getLectureHist(dto));
    }
}
