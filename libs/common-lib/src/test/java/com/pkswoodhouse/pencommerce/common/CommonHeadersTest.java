package com.pkswoodhouse.pencommerce.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonHeadersTest {

    @Test
    void constantsAreAsExpected() {
        assertEquals("X-Trace-Id", CommonHeaders.TRACE_ID);
        assertEquals("X-Correlation-Id", CommonHeaders.CORRELATION_ID);
    }
}
