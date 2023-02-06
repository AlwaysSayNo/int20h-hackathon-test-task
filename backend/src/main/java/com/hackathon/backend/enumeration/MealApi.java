package com.hackathon.backend.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MealApi {

    GET_ALL_CATEGORIES("https://www.themealdb.com/api/json/v1/1/categories.php"),
    GET_ALL_INGREDIENTS("https://www.themealdb.com/api/json/v1/1/list.php?i=list"),
    GET_DISH_BY_ID("https://www.themealdb.com/api/json/v1/1/lookup.php?i=%d");

    private final String path;

}
