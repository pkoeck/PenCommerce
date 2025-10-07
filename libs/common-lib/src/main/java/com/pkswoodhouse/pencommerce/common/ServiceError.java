package com.pkswoodhouse.pencommerce.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public sealed interface ServiceError extends GeneralError {
    record ServiceUnavailableError() implements ServiceError {
        @Override
        public ProblemDetail toProblemDetail() {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, "Service is temporarily unavailable");
            problemDetail.setTitle("Service Unavailable");
            return problemDetail;
        }
    }
    
    record DatabaseOperationError(String operation, String details) implements ServiceError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Database operation failed: " + operation + " - " + details);
        }
    }
    
    record AIProcessingError(String operation, String details) implements ServiceError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "AI processing failed: " + operation + " - " + details);
        }
    }
    
    // Journey System Errors
    record JourneyNotFoundError(String journeyId) implements ServiceError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                "Journey not found: " + journeyId);
        }
    }
    
    record JourneyAlreadyActiveError(String journeyType) implements ServiceError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "Journey already active: " + journeyType);
        }
    }
    
    record JourneyInactiveError(String journeyType) implements ServiceError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Journey template is not active: " + journeyType);
        }
    }
    
    record JourneyNotActiveError(String journeyId) implements ServiceError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Journey is not active: " + journeyId);
        }
    }
    
    record StepNotFoundError(String stepId) implements ServiceError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                "Step not found: " + stepId);
        }
    }
    
    record StepAlreadyCompletedError(String stepId) implements ServiceError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "Step already completed: " + stepId);
        }
    }
    
    record JourneyOperationError(String operation, String details) implements ServiceError {
        @Override
        public ProblemDetail toProblemDetail() {
            return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Journey operation failed: " + operation + " - " + details);
        }
    }
}
