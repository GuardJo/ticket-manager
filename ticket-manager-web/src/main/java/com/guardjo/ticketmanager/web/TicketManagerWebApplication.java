package com.guardjo.ticketmanager.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class TicketManagerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketManagerWebApplication.class, args);
    }

}
