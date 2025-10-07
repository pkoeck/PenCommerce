package com.pkswoodhouse.pencommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class PenCommerceApplicationTests {

    @Test
    void contextLoads() {
    }

}
