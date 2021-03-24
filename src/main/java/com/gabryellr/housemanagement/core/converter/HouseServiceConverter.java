package com.gabryellr.housemanagement.core.converter;

import com.gabryellr.housemanagement.core.model.HouseBo;
import com.gabryellr.housemanagement.data.model.HouseEntity;
import org.springframework.stereotype.Component;

@Component
public class HouseServiceConverter {

    public HouseBo toBO(HouseEntity entity) {
        return HouseBo.builder()
                .id(entity.getId())
                .bedroomNumbers(entity.getBedroomNumbers())
                .hasGarage(entity.getHasGarage())
                .number(entity.getNumber())
                .street(entity.getStreet())
                .city(entity.getCity())
                .zipCode(entity.getZipCode())
                .build();
    }

    public HouseEntity toEntity(HouseBo houseBo) {
        return HouseEntity.builder()
                .hasGarage(houseBo.getHasGarage())
                .bedroomNumbers(houseBo.getBedroomNumbers())
                .number(houseBo.getNumber())
                .street(houseBo.getStreet())
                .city(houseBo.getCity())
                .zipCode(houseBo.getZipCode())
                .build();
    }

}