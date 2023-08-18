package io.github.guardjo.ticketmanager.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class TicketManagerBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketManagerBatchApplication.class, args);
    }
}
