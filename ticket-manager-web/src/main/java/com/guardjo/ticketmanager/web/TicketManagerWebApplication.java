package com.guardjo.ticketmanager.web;

import io.github.guardjo.ticketmanager.batch.config.BatchConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(basePackages = {
        "com.guardjo.ticketmanager.web",
        "io.github.guardjo.ticketmanager.batch.repository"
})
@ConfigurationPropertiesScan
@SpringBootApplication
public class TicketManagerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketManagerWebApplication.class, args);
    }

}
