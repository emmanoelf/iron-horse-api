package com.ironhorse.exception;

import com.ironhorse.dto.ProblemDetail;
import com.ironhorse.dto.ProblemType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFound.class)
    protected ResponseEntity<ProblemDetail> handleEntityNotFound(EntityNotFound ex) {
        ProblemType problemType = ProblemType.ENTITY_NOT_FOUND;

        ProblemDetail problemDetail = createProblemDetailBuilder(
                HttpStatus.NOT_FOUND,
                problemType,
                ex.getMessage()
        ).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    private ProblemDetail.ProblemDetailBuilder createProblemDetailBuilder(HttpStatus status, ProblemType problemType, String detail){
        return ProblemDetail.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }
}
