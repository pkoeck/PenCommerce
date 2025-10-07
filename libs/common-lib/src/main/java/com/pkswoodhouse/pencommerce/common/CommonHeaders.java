package com.pkswoodhouse.pencommerce.common;

/**
 * Common HTTP header names used across PenCommerce services.
 * Keep this library strictly service-agnostic.
 */
public final class CommonHeaders {
    public static final String TRACE_ID = "X-Trace-Id";
    public static final String CORRELATION_ID = "X-Correlation-Id";

    private CommonHeaders() {
    }
}
