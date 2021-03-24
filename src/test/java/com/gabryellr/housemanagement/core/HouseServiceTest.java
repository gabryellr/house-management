package com.gabryellr.housemanagement.core;

import com.gabryellr.housemanagement.core.converter.HouseServiceConverter;
import com.gabryellr.housemanagement.core.model.HouseBo;
import com.gabryellr.housemanagement.data.model.HouseEntity;
import com.gabryellr.housemanagement.data.repository.HouseRepository;
import com.gabryellr.housemanagement.exception.HouseWithSameAddress;
import com.gabryellr.housemanagement.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class HouseServiceTest {

    static final long HOUSE_ID = 123L;

    @InjectMocks
    private HouseService service;

    @Mock
    private HouseRepository repository;

    @Mock
    private HouseServiceConverter converter;

    @Test
    void givenValidIdShouldReturnValidHouse() {
        when(repository.findById(HOUSE_ID)).thenReturn(Optional.of(mockHouseEntity()));
        when(converter.toBO(mockHouseEntity())).thenReturn(mockHouseBo());

        assertDoesNotThrow(() -> service.findHouseById(HOUSE_ID));
    }

    @Test
    void givenInvalidIdShouldThrowNotFoundException() {
        when(repository.findById(HOUSE_ID)).thenReturn(Optional.empty());

        setField(service, "houseIdNotFound", "House with ID was not found.");

        NotFoundException exception = assertThrows(
                NotFoundException.class, () -> service.findHouseById(HOUSE_ID),
                "Expected service.findHouseById to throw when ID was not found"
        );

        assertEquals("House with ID was not found.", exception.getDescription());
    }

    @Test
    void shouldSaveWhenDoesNotHaveError() {
        HouseBo houseBo = mockHouseBo();
        HouseEntity houseEntity = mockHouseEntity();
        when(repository.existsByStreetAndZipCodeAndNumberAndCity(houseBo.getStreet(), houseBo.getZipCode(),
                houseBo.getNumber(), houseBo.getCity())).thenReturn(false);
        when(converter.toEntity(houseBo)).thenReturn(houseEntity);

        service.save(houseBo);

        verify(repository, times(1)).save(houseEntity);
    }

    @Test
    void shouldReturnListOfHouses() {
        HouseEntity houseEntity = mockHouseEntity();
        when(converter.toBO(houseEntity)).thenReturn(mockHouseBo());
        when(repository.findAll()).thenReturn(singletonList(houseEntity));

        List<HouseBo> houses = service.findAll();

        assertEquals(1, houses.size());
    }

    @Test
    void shouldDeleteHouseWhenIdExists() {
        service.deleteHouseById(HOUSE_ID);

        verify(repository, times(1)).deleteById(HOUSE_ID);
    }

    @Test
    void givenInvalidIdShouldThrowExceptionWhenTryToDeleteHouse() {
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(HOUSE_ID);

        setField(service, "houseIdNotFound", "House with ID was not found.");

        NotFoundException exception = assertThrows(
                NotFoundException.class, () -> service.deleteHouseById(HOUSE_ID),
                "Expected service.deleteHouseById to throw when ID was not found"
        );
        assertEquals("House with ID was not found.", exception.getDescription());
    }

    @Test
    void givenHouseWithAddressAlreadyExistsShouldThrowExceptionWhenSave() {
        HouseBo houseBo = mockHouseBo();

        when(repository.existsByStreetAndZipCodeAndNumberAndCity(houseBo.getStreet(), houseBo.getZipCode(),
                houseBo.getNumber(), houseBo.getCity())).thenReturn(true);

        setField(service, "houseAlreadyExistsWithSameAddress", "House with the same address already exists");

        HouseWithSameAddress exception = assertThrows(
                HouseWithSameAddress.class, () -> service.save(houseBo),
                "Expected service.deleteHouseById to throw when house with the same address already exists"
        );
        assertEquals("House with the same address already exists", exception.getDescription());
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