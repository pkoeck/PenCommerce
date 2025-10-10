package com.pkswoodhouse.pencommerce.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public sealed interface MaterialError extends GeneralError {

    record MaterialNotFoundError(long materialId) implements MaterialError {
        @Override
        public ProblemDetail toProblemDetail() {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                    "Material with ID " + materialId  + " not found or not accessible");
            problemDetail.setTitle("Material Not Found");
            return problemDetail;
        }
    }

    record DuplicateMaterialError(String materialName) implements MaterialError {
        @Override
        public ProblemDetail toProblemDetail() {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                    "Material with name " + materialName);
            problemDetail.setTitle("Duplicate Material");

            return problemDetail;
        }
    }
}
