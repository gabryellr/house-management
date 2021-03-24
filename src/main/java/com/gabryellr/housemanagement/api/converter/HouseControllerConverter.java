package com.gabryellr.housemanagement.api.converter;

import com.gabryellr.housemanagement.api.model.HouseDtoInput;
import com.gabryellr.housemanagement.api.model.HouseDtoOutput;
import com.gabryellr.housemanagement.core.model.HouseBo;
import org.springframework.stereotype.Component;

@Component
public class HouseControllerConverter {

    public HouseDtoOutput toDtoOutput(HouseBo houseFound) {
        return HouseDtoOutput.builder()
                .id(houseFound.getId())
                .bedroomNumbers(houseFound.getBedroomNumbers())
                .hasGarage(houseFound.getHasGarage())
                .number(houseFound.getNumber())
                .street(houseFound.getStreet())
                .city(houseFound.getCity())
                .zipCode(houseFound.getZipCode())
                .build();
    }

    public HouseBo toBO(HouseDtoInput dtoInput) {
        return HouseBo.builder()
                .zipCode(dtoInput.getZipCode())
                .street(dtoInput.getStreet())
                .number(dtoInput.getNumber())
                .city(dtoInput.getCity())
                .bedroomNumbers(dtoInput.getBedroomNumbers())
                .hasGarage(dtoInput.getHasGarage())
                .build();
    }

}