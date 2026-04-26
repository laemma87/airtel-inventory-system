package com.inventory.airtel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirtelApplication {

    public static void main(String[] args) {
        // This is the only line needed for a Web Application
        SpringApplication.run(AirtelApplication.class, args);
    }
}