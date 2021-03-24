package com.gabryellr.housemanagement.core;

import com.gabryellr.housemanagement.core.converter.HouseServiceConverter;
import com.gabryellr.housemanagement.core.model.HouseBo;
import com.gabryellr.housemanagement.data.model.HouseEntity;
import com.gabryellr.housemanagement.data.repository.HouseRepository;
import com.gabryellr.housemanagement.exception.HouseWithSameAddress;
import com.gabryellr.housemanagement.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class HouseService {

    @Value("${messages.house_not_found}")
    private String houseIdNotFound;

    @Value("${messages.house_already_exists_with_same_address}")
    private String houseAlreadyExistsWithSameAddress;

    private final HouseRepository repository;
    private final HouseServiceConverter converter;

    public HouseBo findHouseById(Long id) {
        log.info("Finding house with ID {}", id);

        return repository.findById(id)
                .map(converter::toBO)
                .orElseThrow(() -> new NotFoundException(format(houseIdNotFound, id)));
    }

    public void save(HouseBo houseBo) {
        log.info("verifying if already exists a house with address [{}]", houseBo);
        if (hasHouseWithSameAddress(houseBo)) {
            throw new HouseWithSameAddress(format(houseAlreadyExistsWithSameAddress, houseBo.getStreet(),
                    houseBo.getNumber(), houseBo.getZipCode(), houseBo.getCity()));
        }

        HouseEntity houseEntity = converter.toEntity(houseBo);
        repository.save(houseEntity);

        log.info("house saved");
    }

    public List<HouseBo> findAll() {
        log.info("finding all houses");

        return repository.findAll()
                .stream()
                .map(converter::toBO)
                .collect(toList());
    }

    public void deleteHouseById(Long id) {
        log.info("Finding and deleting a house with ID {}", id);
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(format(houseIdNotFound, id));
        }
        log.info("House with ID {} deleted", id);
    }

    private boolean hasHouseWithSameAddress(HouseBo houseBo) {
        return repository.existsByStreetAndZipCodeAndNumberAndCity(houseBo.getStreet(),
                houseBo.getZipCode(), houseBo.getNumber(), houseBo.getCity());
    }

}