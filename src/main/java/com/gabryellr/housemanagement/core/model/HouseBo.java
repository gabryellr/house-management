package com.gabryellr.housemanagement.core.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HouseBo {

    private Long id;
    private String street;
    private String zipCode;
    private Integer number;
    private String city;
    private Integer bedroomNumbers;
    private Boolean hasGarage;

}