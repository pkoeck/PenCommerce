package com.pkswoodhouse.pencommerce.catalog.repositories;

import com.pkswoodhouse.pencommerce.catalog.entities.Material;
import com.pkswoodhouse.pencommerce.catalog.entities.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material>  findMaterialByMaterialType(MaterialType materialType);
}
