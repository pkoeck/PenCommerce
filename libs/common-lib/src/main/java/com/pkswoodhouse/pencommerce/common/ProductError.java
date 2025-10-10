package com.pkswoodhouse.pencommerce.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public sealed interface ProductError extends GeneralError {

    record ProductNotFoundError(long productId) implements ProductError {
        @Override
        public ProblemDetail toProblemDetail() {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                    "Product with ID " + productId + " not found or not accessible");
            problemDetail.setTitle("Product Not Found");
            return problemDetail;
        }
    }

    record DuplicateProductError(String productName) implements ProductError {
        @Override
        public ProblemDetail toProblemDetail() {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                    "Product with name " + productName);
            problemDetail.setTitle("Duplicate Product");

            return problemDetail;
        }
    }
}
