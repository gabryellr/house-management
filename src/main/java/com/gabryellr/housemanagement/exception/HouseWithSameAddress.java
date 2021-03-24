package com.gabryellr.housemanagement.exception;


import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
@Getter
public class HouseWithSameAddress extends RuntimeException {

    private final String description;

    public HouseWithSameAddress(String description) {
        this.description = description;
    }

}