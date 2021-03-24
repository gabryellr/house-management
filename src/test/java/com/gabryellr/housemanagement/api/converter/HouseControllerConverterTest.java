package com.gabryellr.housemanagement.api.converter;

import com.gabryellr.housemanagement.api.model.HouseDtoInput;
import com.gabryellr.housemanagement.api.model.HouseDtoOutput;
import com.gabryellr.housemanagement.core.model.HouseBo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
class HouseControllerConverterTest {

    public static final long HOUSE_ID = 123L;

    private final HouseControllerConverter converter = new HouseControllerConverter();

    @Test
    void givenBoShouldReturnDtoOutput() {
        HouseBo houseBo = mockHouseBo();

        HouseDtoOutput actual = converter.toDtoOutput(houseBo);

        assertEquals(houseBo.getZipCode(), actual.getZipCode());
        assertEquals(houseBo.getStreet(), actual.getStreet());
        assertEquals(houseBo.getHasGarage(), actual.getHasGarage());
        assertEquals(houseBo.getCity(), actual.getCity());
        assertEquals(houseBo.getNumber(), actual.getNumber());
        assertEquals(houseBo.getBedroomNumbers(), actual.getBedroomNumbers());
    }

    @Test
    void toBO() {
        HouseDtoInput expected = mockHouseDtoInput();

        HouseBo actual = converter.toBO(expected);

        assertEquals(expected.getZipCode(), actual.getZipCode());
        assertEquals(expected.getStreet(), actual.getStreet());
        assertEquals(expected.getHasGarage(), actual.getHasGarage());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getNumber(), actual.getNumber());
        assertEquals(expected.getBedroomNumbers(), actual.getBedroomNumbers());

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

    private HouseDtoInput mockHouseDtoInput() {
        return HouseDtoInput.builder()
                .city("Germany")
                .bedroomNumbers(1)
                .hasGarage(true)
                .number(532)
                .street("Zur Windmühle")
                .zipCode("01445")
                .build();
    }

}