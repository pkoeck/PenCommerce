package com.pkswoodhouse.pencommerce.catalog.services;

import com.pkswoodhouse.pencommerce.catalog.entities.Material;
import com.pkswoodhouse.pencommerce.catalog.entities.MaterialType;
import com.pkswoodhouse.pencommerce.catalog.entities.Product;
import com.pkswoodhouse.pencommerce.common.Either;
import com.pkswoodhouse.pencommerce.common.GeneralError;

import java.util.List;

public interface CatalogService {
    Either<GeneralError, Product> GetProductById(long id);
    Either<GeneralError, Product> AddOrUpdateProduct(Product product);
    Either<GeneralError, Void> DeleteProduct(long id);

    Either<GeneralError, Material> getMaterialById(long id);
    Either<GeneralError, List<Material>> getMaterials(MaterialType materialType);
    Either<GeneralError, Material> AddOrUpdateMaterial(Material material);
    Either<GeneralError, Void> DeleteMaterial(long id);
}
