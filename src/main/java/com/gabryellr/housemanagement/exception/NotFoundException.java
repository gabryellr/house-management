package com.gabryellr.housemanagement.exception;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
@Getter
public class NotFoundException extends RuntimeException {

    private final String description;

    public NotFoundException(String description) {
        this.description = description;
    }

}