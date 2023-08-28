package com.guardjo.ticketmanager.web.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@ConfigurationPropertiesScan
@TestConfiguration
@Import({ThymeleafConfig.class, ThymeleafConfig.ThymeleafProperty.class})
public class TestConfig {
}
