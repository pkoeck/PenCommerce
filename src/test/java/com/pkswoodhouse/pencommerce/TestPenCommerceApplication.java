package com.pkswoodhouse.pencommerce;

import org.springframework.boot.SpringApplication;

public class TestPenCommerceApplication {

    public static void main(String[] args) {
        SpringApplication.from(PenCommerceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
