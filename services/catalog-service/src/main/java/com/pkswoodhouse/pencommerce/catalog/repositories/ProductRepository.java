package com.pkswoodhouse.pencommerce.catalog.repositories;

import com.pkswoodhouse.pencommerce.catalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
