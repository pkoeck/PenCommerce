package com.pkswoodhouse.pencommerce.catalog.entities;

import lombok.Getter;

@Getter
public enum MaterialUnitType {
    PEN_BLANK("Pen Blank"),
    GRAMS("Weight in grams"),
    INCHES("Inches"),
    BLANK("Blank");

    private final String displayName;

    MaterialUnitType(String displayName) {
        this.displayName = displayName;
    }
}
