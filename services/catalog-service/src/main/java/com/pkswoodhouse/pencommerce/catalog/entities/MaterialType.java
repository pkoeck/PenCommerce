package com.pkswoodhouse.pencommerce.catalog.entities;

import lombok.Getter;

@Getter
public enum MaterialType {
    KIT("Kit", "Kits for pens or other items like measuring spoons, ice cream scoops, etc."),
    BLANK("Blank", "Pre-cut and dimensioned lengths of wood or other material, typically used for pens"),
    THIN_STOCK("Thin Stock", "Wood veneers, metals, etc.  For layering, segmenting and other effects"),
    INLAY("Inlay Material", "Crushed opals, abalone shell fragments, small crystals, powders, etc.");

    private final String displayName;
    private final String description;

    private MaterialType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}
