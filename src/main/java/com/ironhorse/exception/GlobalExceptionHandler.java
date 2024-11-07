package com.ironhorse.exception;

import com.ironhorse.dto.ProblemDetail;
import com.ironhorse.dto.ProblemObject;
import com.ironhorse.dto.ProblemType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(@Qualifier("messageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({EntityNotFound.class, EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ProblemDetail> handleEntityNotFound(EntityNotFoundException ex) {
        String detail = ex.getMessage();
        return this.buildProblemDetail(HttpStatus.NOT_FOUND, ProblemType.RESOURCE_NOT_FOUND, detail);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ProblemDetail> handleNoResourceFound(HttpServletRequest request) {
        String detail = String.format("O recurso %s que você tentou acessar não foi encontrado", request.getRequestURI());
        return this.buildProblemDetail(HttpStatus.NOT_FOUND, ProblemType.RESOURCE_NOT_FOUND, detail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ProblemDetail> handleValidations(MethodArgumentNotValidException ex){
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

        return this.buildProblemDetail(HttpStatus.BAD_REQUEST, ProblemType.INVALID_DATA, detail, problemObjects);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<ProblemDetail> handleForbiddenAccess(ForbiddenAccessException ex) {
        String detail = ex.getMessage();
        return this.buildProblemDetail(HttpStatus.FORBIDDEN, ProblemType.FORBIDDEN_ACCESS, detail);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<ProblemDetail> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String detail = "Já constam registros deste tipo.";
        return this.buildProblemDetail(HttpStatus.CONFLICT, ProblemType.DUPLICATED_DATA, detail);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ProblemDetail> handleMaxUploadSizeException(MaxUploadSizeExceededException ex) {
        String detail = "Tamanho do arquivo excedido. Tente enviar um arquivo menor que 12MB.";
        return this.buildProblemDetail(HttpStatus.BAD_REQUEST, ProblemType.FILE_SIZE_EXCEEDED, detail);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<ProblemDetail> handleBadCredentialsException(BadCredentialsException ex) {
        String detail = "Email ou senha inválidos";
        return this.buildProblemDetail(HttpStatus.UNAUTHORIZED, ProblemType.BAD_CREDENTIALS, detail);
    }

    private ResponseEntity<ProblemDetail> buildProblemDetail(HttpStatus status, ProblemType problemType, String detail){
        return buildProblemDetail(status, problemType, detail, null);
    }

    private ResponseEntity<ProblemDetail> buildProblemDetail(HttpStatus status, ProblemType problemType, String detail,
                                                              List<ProblemObject> problemObjects) {
        ProblemDetail problemDetail = ProblemDetail.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .problemObjects(problemObjects)
                .build();

        return ResponseEntity.status(status).body(problemDetail);
    }
}
