package com.gabryellr.housemanagement.api;

import com.gabryellr.housemanagement.api.comparator.HouseComparator;
import com.gabryellr.housemanagement.api.converter.HouseControllerConverter;
import com.gabryellr.housemanagement.api.model.HouseDtoInput;
import com.gabryellr.housemanagement.api.model.HouseDtoOutput;
import com.gabryellr.housemanagement.core.HouseService;
import com.gabryellr.housemanagement.core.model.HouseBo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/houses")
public class HouseController {

    private final HouseService service;
    private final HouseControllerConverter converter;

    @GetMapping
    public ResponseEntity<List<HouseDtoOutput>> findAll() {
        List<HouseDtoOutput> houses = service.findAll()
                .stream()
                .map(converter::toDtoOutput)
                .collect(toList());

        HouseComparator houseComparator = new HouseComparator();
        Collections.sort(houses, houseComparator);

        return ResponseEntity.ok(houses);
    }

    @GetMapping("/{houseId}")
    public ResponseEntity<HouseDtoOutput> findHouseById(@PathVariable("houseId") Long id) {
        HouseBo houseFound = service.findHouseById(id);
        log.info("House with ID {} found.", id);

        return ResponseEntity.ok(converter.toDtoOutput(houseFound));
    }

    @DeleteMapping("/{houseId}")
    public ResponseEntity<Void> deleteHouseById(@PathVariable("houseId") Long id) {
        service.deleteHouseById(id);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid HouseDtoInput dtoInput) {
        service.save(converter.toBO(dtoInput));

        return ResponseEntity.status(CREATED).build();
    }
}