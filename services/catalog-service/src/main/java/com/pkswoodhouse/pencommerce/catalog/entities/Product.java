package com.pkswoodhouse.pencommerce.catalog.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @PositiveOrZero
    private Double price;

    @NotBlank
    @Size(max = 255)
    private String description;

    @URL
    @Column(name = "image_uri")
    private String imageURI;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_materials", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "material_id"))
    private List<ProductMaterial> productMaterials;
}
