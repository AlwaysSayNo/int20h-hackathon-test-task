package com.hackathon.backend.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Template {

    INGREDIENT_IMAGE_URL("https://www.themealdb.com/images/ingredients/%s.png");

    private final String value;

}
