package com.gabryellr.housemanagement.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HouseDtoOutput {

    private Long id;
    private String street;
    private String zipCode;
    private String city;
    private Integer number;
    private Integer bedroomNumbers;
    private Boolean hasGarage;

}