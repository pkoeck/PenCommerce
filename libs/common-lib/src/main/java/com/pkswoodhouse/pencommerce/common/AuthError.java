package com.pkswoodhouse.pencommerce.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public sealed interface AuthError extends GeneralError {
    record BadCredentialsError(String email) implements AuthError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
    }
    
    record UnauthorizedAccessError(String email) implements AuthError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "User is not authorized to access this resource");
        }
    }
    
    record AccountNotActiveError(String email) implements AuthError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Account is not active: " + email);
        }
    }
    
    record AccountNotVerifiedError(String email) implements AuthError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Account is not verified: " + email);
        }
    }
    
    record NoAuthenticatedUserError() implements AuthError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "No authenticated user found");
        }
    }
}
