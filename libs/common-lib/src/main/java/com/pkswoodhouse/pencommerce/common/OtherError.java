package com.pkswoodhouse.pencommerce.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public sealed interface OtherError extends GeneralError {
    record InternalError() implements OtherError {
        @Override
        public ProblemDetail toProblemDetail() {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
            problemDetail.setTitle("Internal Server Error");
            return problemDetail;
        }
    }
}
