package com.pkswoodhouse.pencommerce.catalog.controllers;

import com.pkswoodhouse.pencommerce.catalog.dtos.MaterialDto;
import com.pkswoodhouse.pencommerce.catalog.dtos.ProductDto;
import com.pkswoodhouse.pencommerce.catalog.services.CatalogService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/catalog")
@AllArgsConstructor
public class CatalogServiceController {
    private final Logger logger = LoggerFactory.getLogger(CatalogServiceController.class);

    private final CatalogService catalogService;

    // Get a Material by Id
    @GetMapping("/materials/{id}")
    public ResponseEntity<?> getMaterialById(@PathVariable Long id) {
        return catalogService.getMaterialById(id)
                .fold(error -> ResponseEntity.status(error.toProblemDetail().getStatus()).body(error.toProblemDetail()),
                material -> ResponseEntity.ok(MaterialDto.fromEntity(material)));
    }

    // Get all Materials
    @GetMapping("/materials")
    public ResponseEntity<?> getMaterials(@RequestParam(required = false) String materialType) {

    }

    // Get list of materials by material type

    // Get a Product by Id
    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return catalogService.GetProductById(id)
                .fold(error -> ResponseEntity.status(error.toProblemDetail().getStatus()).body(error.toProblemDetail()),
                product -> ResponseEntity.ok(ProductDto.fromEntity(product)));
    }

    // Get all Products
    // Get list of products by material type
    // Material CRUD
    // Product CRUD
}
