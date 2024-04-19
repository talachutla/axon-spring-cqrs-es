package com.targa.labs.dev.cqrsesv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CqrsEsApplicationService2 {

    public static void main(String[] args) {
        System.setProperty("server.port", "8083");
        SpringApplication.run(CqrsEsApplicationService2.class, args);
    }

}
