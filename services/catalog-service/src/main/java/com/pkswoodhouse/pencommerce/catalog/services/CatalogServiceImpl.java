package com.pkswoodhouse.pencommerce.catalog.services;

import com.pkswoodhouse.pencommerce.catalog.entities.Material;
import com.pkswoodhouse.pencommerce.catalog.entities.MaterialType;
import com.pkswoodhouse.pencommerce.catalog.entities.Product;
import com.pkswoodhouse.pencommerce.catalog.repositories.MaterialRepository;
import com.pkswoodhouse.pencommerce.catalog.repositories.ProductRepository;
import com.pkswoodhouse.pencommerce.common.*;
import com.pkswoodhouse.pencommerce.common.Try.Success;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CatalogServiceImpl implements CatalogService {
    private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);

    private final MaterialRepository materialRepository;
    private final ProductRepository productRepository;

    public Either<GeneralError, Product> GetProductById(long id) {
        return productRepository.findById(id)
                .map(Either::<GeneralError, Product>right)
                .orElseGet(() -> Either.left(new ProductError.ProductNotFoundError(id)));
    }

    @Override
    public Either<GeneralError, Product> AddOrUpdateProduct(Product product) {
        return switch (Try.of(() -> productRepository.save(product))) {
            case Try.Value<Product>(var productSaved) -> Either.right(productSaved);
            case Try.Failure(Exception ex) -> Either.left(new ServiceError.DatabaseOperationError("Save Product", ex.getLocalizedMessage()));
        };
    }

    @Override
    public Either<GeneralError, Void> DeleteProduct(long id) {
        return switch (Try.run(() -> productRepository.deleteById(id))) {
            case Success ignored -> Either.right((Void)null);
            case Try.Failure(Exception ex) -> Either.left(new ServiceError.DatabaseOperationError("Delete Product", ex.getLocalizedMessage()));
        };
    }

    @Override
    public Either<GeneralError, Material> getMaterialById(long id) {
        return materialRepository.findById(id)
                .map(Either::<GeneralError, Material>right)
                .orElseGet(() -> Either.left(new MaterialError.MaterialNotFoundError(id)));
    }

    @Override
    public Either<GeneralError, List<Material>> getMaterials(MaterialType materialType) {
        if (materialType == null) {
            return Either.right(materialRepository::findAll);
        }

        return Either.right(materialRepository.findMaterialByMaterialType(materialType));
    }

    @Override
    public Either<GeneralError, Material> AddOrUpdateMaterial(Material material) {
        return switch (Try.of(() -> materialRepository.save(material))) {
            case Try.Value<Material>(var materialSaved) -> Either.right(materialSaved);
            case Try.Failure(Exception ex) -> Either.left(new ServiceError.DatabaseOperationError("Save Material", ex.getLocalizedMessage()));
        };
    }

    @Override
    public Either<GeneralError, Void> DeleteMaterial(long id) {
        return switch (Try.run(() -> materialRepository.deleteById(id))) {
            case Success ignored -> Either.right((Void)null);
            case Try.Failure(Exception ex) -> Either.left(new ServiceError.DatabaseOperationError("Delete Material", ex.getLocalizedMessage()));
        };
}
}
