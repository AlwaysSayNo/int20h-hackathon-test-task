package com.hackathon.backend.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategory {

    BREAD(
            "bread",
            "https://images.pexels.com/photos/1775043/pexels-photo-1775043.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    ),
    MEAT(
            "meat",
            "https://images.pexels.com/photos/65175/pexels-photo-65175.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    ),
    FISH(
            "fish",
            "https://images.pexels.com/photos/14062111/pexels-photo-14062111.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    ),
    CHEESE(
            "cheese",
            "https://images.pexels.com/photos/821365/pexels-photo-821365.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    ),
    DAIRY(
            "dairy",
            "https://images.pexels.com/photos/236010/pexels-photo-236010.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    ),
    FRUIT(
            "fruit",
            "https://images.pexels.com/photos/1132047/pexels-photo-1132047.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    ),
    GRAIN(
            "grain",
            "https://images.pexels.com/photos/326082/pexels-photo-326082.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    ),
    OIL(
            "oil",
            "https://images.pexels.com/photos/1022385/pexels-photo-1022385.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    ),
    SEA_FOOD(
            "sea food",
            "https://images.pexels.com/photos/725992/pexels-photo-725992.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    ),
    SEASONING(
            "seasoning",
            "https://images.pexels.com/photos/4199098/pexels-photo-4199098.jpeg?auto=compress&cs=tinysrgb&w=1200"
    ),
    VEGETABLE(
            "vegetable",
            "https://images.pexels.com/photos/1656666/pexels-photo-1656666.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    ),
    SAUCE(
            "sauce",
            "https://images.pexels.com/photos/1435901/pexels-photo-1435901.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    ),
    ALCOHOL(
            "alcohol",
            "https://images.pexels.com/photos/1283219/pexels-photo-1283219.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    );

    private final String name;

    private final String defaultImageUrl;
}
