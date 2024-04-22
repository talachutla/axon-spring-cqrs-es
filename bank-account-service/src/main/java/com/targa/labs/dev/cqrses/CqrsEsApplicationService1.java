package com.targa.labs.dev.cqrses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CqrsEsApplicationService1 {

    public static void main(String[] args) {
        System.setProperty("server.port", "8082");
        SpringApplication.run(CqrsEsApplicationService1.class, args);
    }

}
