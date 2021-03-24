package com.gabryellr.housemanagement.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(toList());

        log.info("error to receive a body, errors {}", errors);
        return new ResponseEntity<>(buildApiError(request.getContextPath(), errors, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        log.info(ex.getDescription());
        ApiError apiError = buildApiError(ex.getDescription(), emptyList(), NOT_FOUND);

        return status(NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(HouseWithSameAddress.class)
    public ResponseEntity<ApiError> handleAlreadyHouseWithSameAddress(HouseWithSameAddress ex) {
        log.info(ex.getDescription());
        return status(CONFLICT).body(buildApiError(ex.getDescription(), emptyList(), CONFLICT));
    }

    private ApiError buildApiError(String description, List<String> errors, HttpStatus status) {
        return ApiError.builder()
                .description(description)
                .status(status.value())
                .errors(errors)
                .build();
    }

}