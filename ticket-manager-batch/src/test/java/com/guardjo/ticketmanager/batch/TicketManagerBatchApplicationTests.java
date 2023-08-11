package com.guardjo.ticketmanager.batch;

import com.guardjo.ticketmanager.batch.config.TestBatchConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes = {TestBatchConfig.class})
class TicketManagerBatchApplicationTests {

    @Test
    void contextLoads() {
    }

}
