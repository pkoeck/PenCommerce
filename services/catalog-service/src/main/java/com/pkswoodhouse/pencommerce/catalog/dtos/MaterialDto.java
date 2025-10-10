package com.pkswoodhouse.pencommerce.catalog.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pkswoodhouse.pencommerce.catalog.entities.Material;
import com.pkswoodhouse.pencommerce.catalog.entities.MaterialType;
import com.pkswoodhouse.pencommerce.catalog.entities.MaterialUnitType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

/**
 * DTO for {@link com.pkswoodhouse.pencommerce.catalog.entities.Material}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MaterialDto(Long id, @NotNull MaterialType materialType, @Size(max = 255) @NotBlank String name,
                          @Size(max = 255) String description, @URL String imageURI,
                          @NotNull @PositiveOrZero Double price, @NotNull MaterialUnitType materialUnitType,
                          @NotNull @PositiveOrZero Double quantity) implements Serializable {

    public static MaterialDto fromEntity(Material material) {
        return new MaterialDto(material.getId(), material.getMaterialType(), material.getName(), material.getDescription(),
                material.getImageURI(), material.getPrice(), material.getMaterialUnitType(), material.getQuantity());
    }
}
