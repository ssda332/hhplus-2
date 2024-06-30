package hhplus.lectures.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = AlreadyAppliedException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyAppliedException(Exception e) {
        return ResponseEntity.status(409).body(new ErrorResponse("409", "이미 신청하셨습니다."));
    }

    @ExceptionHandler(value = LectureNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLectureNotFoundException(Exception e) {
        return ResponseEntity.status(404).body(new ErrorResponse("404", "해당 특강이 존재하지 않습니다."));
    }

    @ExceptionHandler(value = LectureOptionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLectureOptionNotFoundException(Exception e) {
        return ResponseEntity.status(404).body(new ErrorResponse("404", "해당 특강 정보가 존재하지 않습니다."));
    }

    @ExceptionHandler(value = ExceededLectureException.class)
    public ResponseEntity<ErrorResponse> handleExceededLectureException(Exception e) {
        return ResponseEntity.status(409).body(new ErrorResponse("409", "특강 수강 신청 인원이 다 찼습니다."));
    }

    @ExceptionHandler(value = LectureHistNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLectureHistNotFoundException(Exception e) {
        return ResponseEntity.status(409).body(new ErrorResponse("404", "특강 신청 이력이 존재하지 않습니다."));
    }
}