package com.targa.labs.dev.gateway.rest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.targa.labs.dev.cqrses",
        "com.targa.labs.dev.gateway.rest",
        "com.targa.labs.dev.cqrsesv2"})
public class CqrsEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqrsEsApplication.class, args);
    }

}
