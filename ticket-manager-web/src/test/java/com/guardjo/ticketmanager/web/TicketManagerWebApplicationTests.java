package com.guardjo.ticketmanager.web;

import com.guardjo.ticketmanager.web.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
class TicketManagerWebApplicationTests {

    @Test
    void contextLoads() {
    }

}
