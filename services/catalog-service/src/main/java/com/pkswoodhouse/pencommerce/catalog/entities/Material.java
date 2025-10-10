package com.pkswoodhouse.pencommerce.catalog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.URL;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MaterialType materialType;

    @Column(unique = true)
    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    @URL
    @Column(name = "image_uri")
    private String imageURI;

    @NotNull
    @PositiveOrZero
    private Double price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MaterialUnitType materialUnitType;

    @NotNull
    @PositiveOrZero
    private Double quantity;
}
