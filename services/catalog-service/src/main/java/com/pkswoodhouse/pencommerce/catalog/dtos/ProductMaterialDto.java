package com.pkswoodhouse.pencommerce.catalog.dtos;

import com.pkswoodhouse.pencommerce.catalog.entities.ProductMaterial;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record ProductMaterialDto(@NotNull MaterialDto material, @PositiveOrZero int quantity) {

    public static ProductMaterialDto fromEntity(ProductMaterial pm) {
        if (pm == null)
            return null;

        return new ProductMaterialDto(MaterialDto.fromEntity(pm.getMaterial()), pm.getQuantityUsed());
    }
}
