package com.guardjo.ticketmanager.web.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@ConfigurationPropertiesScan
@ContextConfiguration(classes = {ThymeleafConfig.ThymeleafProperty.class, ThymeleafConfig.class})
public class TestConfig {
}
