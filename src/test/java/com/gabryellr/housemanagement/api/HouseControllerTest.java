package com.gabryellr.housemanagement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.gabryellr.housemanagement.api.converter.HouseControllerConverter;
import com.gabryellr.housemanagement.api.model.HouseDtoInput;
import com.gabryellr.housemanagement.api.model.HouseDtoOutput;
import com.gabryellr.housemanagement.core.HouseService;
import com.gabryellr.housemanagement.core.model.HouseBo;
import com.gabryellr.housemanagement.data.repository.HouseRepository;
import com.gabryellr.housemanagement.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.fasterxml.jackson.databind.SerializationFeature.WRAP_ROOT_VALUE;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HouseController.class)
class HouseControllerTest {

    public static final String BASE_URL = "http://localhost:8080/api/v1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseService service;

    @MockBean
    private HouseRepository repository;

    @MockBean
    private HouseControllerConverter converter;

    @Test
    void givenGetHouseShouldReturnList() throws Exception {
        when(service.findAll()).thenReturn(singletonList(mockHouseBo()));
        when(converter.toDtoOutput(mockHouseBo())).thenReturn(mockHouseDtoOutput());

        MockHttpServletRequestBuilder request = get(BASE_URL + "/houses");

        mockMvc.perform(request)
                .andExpect(jsonPath("$[0].city")
                        .value("Germany"))
                .andExpect(status().isOk());
    }

    @Test
    void ShouldReturnNotFoundWhenHouseIdIsNotValid() throws Exception {
        when(service.findHouseById(1L)).thenThrow(NotFoundException.class);

        MockHttpServletRequestBuilder request = get(BASE_URL + "/houses/{houseId}", 1L);

        mockMvc.perform(request)
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void ShouldReturnHouseDtoOutputWhenIdIsValid() throws Exception {
        when(service.findHouseById(1L)).thenReturn(mockHouseBo());
        when(converter.toDtoOutput(mockHouseBo())).thenReturn(mockHouseDtoOutput());

        MockHttpServletRequestBuilder request = get(BASE_URL + "/houses/{houseId}", 1L);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city")
                        .value("Germany"));
    }

    @Test
    void ShouldReturnNotFoundWhenTryDeleteHouseWithIdInvalid() throws Exception {
        doThrow(NotFoundException.class).when(service).deleteHouseById(1L);

        MockHttpServletRequestBuilder request = delete(BASE_URL + "/houses/{houseId}", 1L);

        mockMvc.perform(request)
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void ShouldReturnNoContentWhenTryDeleteHouseWithIdValid() throws Exception {
        MockHttpServletRequestBuilder request = delete(BASE_URL + "/houses/{houseId}", 1L);

        mockMvc.perform(request)
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void ShouldReturnCreatedWhenSaveAValidHouse() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(mockHouseDtoInput());

        MockHttpServletRequestBuilder request = post(BASE_URL + "/houses")
                .contentType(APPLICATION_JSON)
                .content(requestJson);

        mockMvc.perform(request)
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    private HouseDtoOutput mockHouseDtoOutput() {
        return HouseDtoOutput.builder()
                .city("Germany")
                .bedroomNumbers(1)
                .hasGarage(true)
                .number(532)
                .street("Zur Windmühle")
                .zipCode("01445")
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

    private HouseBo mockHouseBo() {
        return HouseBo.builder()
                .city("Germany")
                .bedroomNumbers(1)
                .hasGarage(true)
                .number(532)
                .street("Zur Windmühle")
                .zipCode("01445")
                .id(2L)
                .build();
    }

}