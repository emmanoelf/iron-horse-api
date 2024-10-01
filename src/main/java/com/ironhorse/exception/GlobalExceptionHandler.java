package com.ironhorse.exception;

import com.ironhorse.dto.ProblemDetail;
import com.ironhorse.dto.ProblemObject;
import com.ironhorse.dto.ProblemType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(@Qualifier("messageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EntityNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ProblemDetail> handleEntityNotFound(EntityNotFound ex) {
        ProblemType problemType = ProblemType.ENTITY_NOT_FOUND;

        ProblemDetail problemDetail = createProblemDetailBuilder(
                HttpStatus.NOT_FOUND,
                problemType,
                ex.getMessage()
        ).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ProblemDetail> handleValidations(MethodArgumentNotValidException ex){
        ProblemType problemType = ProblemType.INVALID_DATA;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<ProblemObject> problemObjects = ex.getBindingResult().getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    String name = objectError.getObjectName();

                    if(objectError instanceof FieldError){
                        name = ((FieldError) objectError).getField();
                    }

                    return ProblemObject.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        ProblemDetail problemDetail = createProblemDetailBuilder(HttpStatus.BAD_REQUEST, problemType, detail)
                .problemObjects(problemObjects)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<ProblemDetail> handleForbiddenAccess(ForbiddenAccessException ex) {
        ProblemType problemType = ProblemType.FORBIDDEN_ACCESS; // Defina um tipo apropriado

        ProblemDetail problemDetail = createProblemDetailBuilder(
                HttpStatus.FORBIDDEN,
                problemType,
                ex.getMessage()
        ).build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }

    private ProblemDetail.ProblemDetailBuilder createProblemDetailBuilder(HttpStatus status, ProblemType problemType, String detail){
        return ProblemDetail.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }
}
