package com.hackathon.backend.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DishSortBy {
    DIFFICULTY("difficulty");
    private final String sortBy;
}
