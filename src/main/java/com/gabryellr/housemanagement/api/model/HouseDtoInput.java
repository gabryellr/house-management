package com.gabryellr.housemanagement.api.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@Builder
public class HouseDtoInput {

    private Integer bedroomNumbers;

    @NotBlank(message = "Street cannot be null or contains only whitespace")
    private String street;

    @NotBlank(message = "Zip code cannot be null or contains only whitespace")
    private String zipCode;

    @NotBlank(message = "City cannot be null or contains only whitespace")
    private String city;

    @NotNull(message = "House number cannot be null")
    private Integer number;

    @NotNull(message = "Garage cannot be null")
    private Boolean hasGarage;

}