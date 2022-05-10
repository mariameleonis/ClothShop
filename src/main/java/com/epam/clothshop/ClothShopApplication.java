package com.epam.clothshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class ClothShopApplication {

    public static void main(String[] args) {

        SpringApplication.run(ClothShopApplication.class, args);
    }
}
