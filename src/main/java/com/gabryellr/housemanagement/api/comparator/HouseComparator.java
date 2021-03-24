package com.gabryellr.housemanagement.api.comparator;

import com.gabryellr.housemanagement.api.model.HouseDtoOutput;

import java.util.Comparator;

public class HouseComparator implements Comparator<HouseDtoOutput> {

    @Override
    public int compare(HouseDtoOutput o1, HouseDtoOutput o2) {
        return Long.compare(o1.getId(), o2.getId());
    }
}
