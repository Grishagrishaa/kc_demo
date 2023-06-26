package ru.clevertec.kc_demo.controller.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.kc_demo.dto.errors.ErrorMessage;
import ru.clevertec.kc_demo.dto.errors.StructuredError;


import java.net.ConnectException;
import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handle(IllegalArgumentException e, HttpServletRequest req){
        return ResponseEntity
                .badRequest()
                .body(buildErrorMessage(e.getMessage(), BAD_REQUEST, req.getRequestURI()));

    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorMessage> handle(PSQLException e, HttpServletRequest req) {
        return ResponseEntity
                .badRequest()
                .body(buildErrorMessage(e.getServerErrorMessage().getDetail(), BAD_REQUEST, req.getRequestURI()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(EntityNotFoundException e, HttpServletRequest req){
        return ResponseEntity
                .status(NO_CONTENT)
                .body(buildErrorMessage("Entity not found", NO_CONTENT, req.getRequestURI()));
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ErrorMessage> handle(OptimisticLockException e, HttpServletRequest req){
        return ResponseEntity
                .status(CONFLICT)
                .body(buildErrorMessage(e.getMessage(), CONFLICT, req.getRequestURI()));
    }


    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ErrorMessage> handle(ConnectException e, HttpServletRequest req){
        return ResponseEntity
                .internalServerError()
                .body(buildErrorMessage("SERVICE ISN'T RESPONDING", INTERNAL_SERVER_ERROR, req.getRequestURI()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(UsernameNotFoundException e, HttpServletRequest req){
        return ResponseEntity
                .badRequest()
                .body(buildErrorMessage(e.getMessage(), BAD_REQUEST, req.getRequestURI()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StructuredError> handle(ConstraintViolationException e, HttpServletRequest req){

        Set<ErrorMessage> errorMessages = e.getConstraintViolations().stream()
                .map(violation -> ErrorMessage.builder()
                        .uri(StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                                .reduce((first, second) -> second)
                                .orElse(null)
                                .toString())
                        .message(violation.getMessage())
                        .build())
                .collect(toSet());

        return ResponseEntity.badRequest().body(StructuredError.builder()
                .errors(errorMessages)
                .status(BAD_REQUEST)
                .uri(req.getRequestURI())
                .build());
    }

    private ErrorMessage buildErrorMessage(String message, HttpStatus status, String uri){
        return ErrorMessage.builder()
                .message(message)
                .status(status)
                .uri(uri)
                .build();
    }
}
