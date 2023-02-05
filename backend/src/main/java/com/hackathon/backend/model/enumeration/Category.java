package com.hackathon.backend.model.enumeration;

import lombok.Getter;

@Getter
public enum Category {

    BEEF ("Beef"),
    CHICKEN("Chicken"),
    DESSERT("Dessert"),
    LAMB("Lamb"),
    MISCELLANEOUS("Miscellaneous"),
    PASTA("Pasta"),
    PORK("Pork"),
    SEAFOOD("Seafood"),
    SIDE("Side"),
    STARTER("Starter"),
    VEGAN("Vegan"),
    VEGETARIAN("Vegetarian"),
    BREAKFAST("Breakfast"),
    GOAT("Goat");

    private final String name;

    Category(String name) {
        this.name = name;
    }

}
