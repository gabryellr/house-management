package com.gabryellr.housemanagement.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "house")
public class HouseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String street;
    private String zipCode;
    private Integer number;
    private String city;
    private Integer bedroomNumbers;
    private Boolean hasGarage;

}