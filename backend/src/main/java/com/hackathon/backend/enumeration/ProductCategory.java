package com.hackathon.backend.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategory {

    BREAD("bread"),
    MEAT("meat"),
    FISH("fish"),
    CHEESE("cheese"),
    DAIRY("dairy"),
    FRUIT("fruit"),
    GRAIN("grain"),
    OIL("oil"),
    SEA_FOOD("sea food"),
    SEASONING("seasoning"),
    VEGETABLE("vegetable"),
    SAUCE("sauce"),
    ALCOHOL("alcohol");

    private final String name;

}
