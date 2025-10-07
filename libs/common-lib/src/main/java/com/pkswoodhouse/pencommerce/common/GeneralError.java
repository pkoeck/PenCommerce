package com.pkswoodhouse.pencommerce.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.springframework.http.ProblemDetail.*;

public sealed interface GeneralError permits OtherError, AuthError, UserError, ServiceError, LeadError {
    default ProblemDetail toProblemDetail() {
        return forStatusAndDetail(HttpStatus.BAD_REQUEST, "Bad Request");
    }
}
