package com.pkswoodhouse.pencommerce.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public sealed interface UserError extends GeneralError {
    record UserNotFoundError(String email) implements UserError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, 
                "User not found: " + email
            );
        }
    }
    
    record UserAlreadyExistsError(String email) implements UserError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, 
                "User already exists: " + email
            );
        }
    }
    
    record UserNotActiveError(String email) implements UserError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN, 
                "User account is not active: " + email
            );
        }
    }
    
    record UserNotAuthorizedError(String email) implements UserError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED, 
                "User not authorized: " + email
            );
        }
    }
    
    record UserNotSubscribedError(String email) implements UserError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(
                HttpStatus.PAYMENT_REQUIRED, 
                "User subscription required: " + email
            );
        }
    }
    
    record UserNotEligibleError(String email) implements UserError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN, 
                "User not eligible: " + email
            );
        }
    }
    
    record UserNotEligibleForServiceError(String email) implements UserError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN, 
                "User not eligible for service: " + email
            );
        }
    }
    
    record UserNotEligibleForSpecializationError(String email) implements UserError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN, 
                "User not eligible for specialization: " + email
            );
        }
    }
}
