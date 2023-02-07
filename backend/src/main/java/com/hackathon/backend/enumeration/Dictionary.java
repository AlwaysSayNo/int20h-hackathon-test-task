package com.hackathon.backend.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Dictionary {

    BREAD_PATH("meal/dictionary/breads.txt"),
    MEAT_PATH("meal/dictionary/meats.txt"),
    FISH_PATH("meal/dictionary/fish.txt"),
    CHEESE_PATH("meal/dictionary/cheeses.txt"),
    DAIRY_PATH("meal/dictionary/dairies.txt"),
    FRUIT_PATH("meal/dictionary/fruits.txt"),
    GRAIN_PATH("meal/dictionary/grains.txt"),
    OIL_PATH("meal/dictionary/oils.txt"),
    SEA_FOOD_PATH("meal/dictionary/sea.txt"),
    SEASONING_PATH("meal/dictionary/seasonings.txt"),
    VEGETABLE_PATH("meal/dictionary/vegetables.txt"),
    SAUCE_PATH("meal/dictionary/sauces.txt"),
    ALCOHOL_PATH("meal/dictionary/alcohols.txt");

    private String path;

}
