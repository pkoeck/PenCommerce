package com.pkswoodhouse.pencommerce.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public sealed interface LeadError extends GeneralError {
    
    record LeadNotFoundError(Long leadId) implements LeadError {
        @Override
        public ProblemDetail toProblemDetail() {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, 
                "Lead with ID " + leadId + " not found or not accessible");
            problemDetail.setTitle("Lead Not Found");
            return problemDetail;
        }
    }
    
    record DuplicateLeadError(String email) implements LeadError {
        @Override
        public ProblemDetail toProblemDetail() {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "A lead with email " + email + " already exists for this agent");
            problemDetail.setTitle("Duplicate Lead");
            return problemDetail;
        }
    }
    
    record InvalidLeadDataError(String field, String reason) implements LeadError {
        @Override
        public ProblemDetail toProblemDetail() {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Invalid lead data for field '" + field + "': " + reason);
            problemDetail.setTitle("Invalid Lead Data");
            return problemDetail;
        }
    }
    
    record LeadAccessDeniedError(Long leadId) implements LeadError {
        @Override
        public ProblemDetail toProblemDetail() {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,
                "Access denied to lead " + leadId);
            problemDetail.setTitle("Lead Access Denied");
            return problemDetail;
        }
    }
    
    record LeadActivityNotFoundError(Long activityId) implements LeadError {
        @Override
        public ProblemDetail toProblemDetail() {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                "Lead activity with ID " + activityId + " not found");
            problemDetail.setTitle("Activity Not Found");
            return problemDetail;
        }
    }
}
