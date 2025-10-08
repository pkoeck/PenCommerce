package com.pkswoodhouse.pencommerce.catalog.entities;

import lombok.Getter;

@Getter
public enum ProductType {
    PRE_MADE("Pre Made"),
    CUSTOM("Custom Made");

    private final String displayName;

    ProductType(String displayName) {
        this.displayName = displayName;
    }
}
