package com.pkswoodhouse.pencommerce.catalog.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pkswoodhouse.pencommerce.catalog.entities.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * DTO for {@link com.pkswoodhouse.pencommerce.catalog.entities.Product}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductDto(Long id, @Size(max = 255) @NotBlank String name, @NotNull ProductType productType,
                         @PositiveOrZero Double price, @Size(max = 255) @NotBlank String description,
                         @URL String imageURI, List<ProductMaterialDto> productMaterials) implements Serializable {
    public static ProductDto fromEntity(com.pkswoodhouse.pencommerce.catalog.entities.Product product) {
        if (product == null)
            return null;

        return new ProductDto(product.getId(),
                product.getName(),
                product.getProductType(),
                product.getPrice(),
                product.getDescription(),
                product.getImageURI(),
                product.getProductMaterials()
                        .stream()
                        .map(ProductMaterialDto::fromEntity)
                        .toList());
    }
}
