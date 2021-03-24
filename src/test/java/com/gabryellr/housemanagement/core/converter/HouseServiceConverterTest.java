package com.gabryellr.housemanagement.core.converter;

import com.gabryellr.housemanagement.core.model.HouseBo;
import com.gabryellr.housemanagement.data.model.HouseEntity;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
class HouseServiceConverterTest {

    public static final long HOUSE_ID = 123L;

    private final HouseServiceConverter converter = new HouseServiceConverter();

    @Test
    void givenHouseEntityShouldReturnHouseBo() {
        HouseEntity houseEntity = mockHouseEntity();

        HouseBo houseBo = converter.toBO(houseEntity);

        assertEquals(houseEntity.getBedroomNumbers(), houseBo.getBedroomNumbers());
        assertEquals(houseEntity.getNumber(), houseBo.getNumber());
        assertEquals(houseEntity.getCity(), houseBo.getCity());
        assertEquals(houseEntity.getHasGarage(), houseBo.getHasGarage());
        assertEquals(houseEntity.getStreet(), houseBo.getStreet());
        assertEquals(houseEntity.getZipCode(), houseBo.getZipCode());
    }

    @Test
    void givenHouseBoShouldReturnHouseEntity() {
        HouseBo houseBo = mockHouseBo();

        HouseEntity houseEntity = converter.toEntity(houseBo);

        assertEquals(houseBo.getBedroomNumbers(), houseEntity.getBedroomNumbers());
        assertEquals(houseBo.getNumber(), houseEntity.getNumber());
        assertEquals(houseBo.getCity(), houseEntity.getCity());
        assertEquals(houseBo.getHasGarage(), houseEntity.getHasGarage());
        assertEquals(houseBo.getStreet(), houseEntity.getStreet());
        assertEquals(houseBo.getZipCode(), houseEntity.getZipCode());
    }

    private HouseBo mockHouseBo() {
        return HouseBo.builder()
                .city("Germany")
                .bedroomNumbers(1)
                .hasGarage(true)
                .number(532)
                .street("Zur Windmühle")
                .zipCode("01445")
                .id(HOUSE_ID)
                .build();
    }

    private HouseEntity mockHouseEntity() {
        return HouseEntity.builder()
                .city("Germany")
                .bedroomNumbers(1)
                .hasGarage(true)
                .number(532)
                .street("Zur Windmühle")
                .zipCode("01445")
                .id(HOUSE_ID)
                .build();
    }
}