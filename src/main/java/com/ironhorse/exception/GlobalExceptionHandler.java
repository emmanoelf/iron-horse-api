package com.ironhorse.exception;

import com.ironhorse.dto.ProblemDetail;
import com.ironhorse.dto.ProblemObject;
import com.ironhorse.dto.ProblemType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.servlet.multipart.max-file-size}")
    private Long maxFileSize;
    private final MessageSource messageSource;

    public GlobalExceptionHandler(@Qualifier("messageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({EntityNotFound.class, EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ProblemDetail> handleEntityNotFound(EntityNotFoundException ex) {
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;

        ProblemDetail problemDetail = createProblemDetailBuilder(
                HttpStatus.NOT_FOUND,
                problemType,
                ex.getMessage()
        ).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ProblemDetail> handleNoResourceFound(HttpServletRequest request) {
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;

        String detail = String.format("O recurso %s que você tentou acessar não foi encontrado", request.getRequestURI());


        ProblemDetail problemDetail = createProblemDetailBuilder(
                HttpStatus.NOT_FOUND,
                problemType,
                detail
        ).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ProblemDetail> handleValidations(MethodArgumentNotValidException ex) {
        ProblemType problemType = ProblemType.INVALID_DATA;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<ProblemObject> problemObjects = ex.getBindingResult().getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
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
        ProblemType problemType = ProblemType.FORBIDDEN_ACCESS;

        ProblemDetail problemDetail = createProblemDetailBuilder(
                HttpStatus.FORBIDDEN,
                problemType,
                ex.getMessage()
        ).build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<ProblemDetail> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ProblemType problemType = ProblemType.DUPLICATED_DATA;
        String detail = "Já constam registros deste tipo.";

        ProblemDetail problemDetail = createProblemDetailBuilder(
                HttpStatus.CONFLICT,
                problemType,
                detail
        ).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ProblemDetail> handleMaxUploadSizeException(MaxUploadSizeExceededException ex) {
        ProblemType problemType = ProblemType.FILE_SIZE_EXCEEDED;

        String detail = "Tamanho do arquivo excedido. Tente enviar um arquivo menor que "
                + (maxFileSize / (1024.0 * 1024.0)) + " MB";

        ProblemDetail problemDetail = createProblemDetailBuilder(
                HttpStatus.BAD_REQUEST,
                problemType,
                detail
        ).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<ProblemDetail> handleBadCredentialsException(BadCredentialsException ex) {
        ProblemType problemType = ProblemType.BAD_CREDENTIALS;

        String detail = "Email ou senha inválidos";

        ProblemDetail problemDetail = createProblemDetailBuilder(
                HttpStatus.UNAUTHORIZED,
                problemType,
                detail
        ).build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException ex) {
        ProblemType problemType = ProblemType.INVALID_DATA;

        String detail = ex.getMessage();

        ProblemDetail problemDetail = createProblemDetailBuilder(
                HttpStatus.BAD_REQUEST,
                problemType,
                detail
        ).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ProblemDetail> handleBusinessException(BusinessException ex) {
        ProblemType problemType = ProblemType.INVALID_DATA;

        String detail = ex.getMessage();

        ProblemDetail problemDetail = createProblemDetailBuilder(
                HttpStatus.BAD_REQUEST,
                problemType,
                detail
        ).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    private ProblemDetail.ProblemDetailBuilder createProblemDetailBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return ProblemDetail.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }
}
