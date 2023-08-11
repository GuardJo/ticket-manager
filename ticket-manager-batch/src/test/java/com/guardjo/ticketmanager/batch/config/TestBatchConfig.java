package com.guardjo.ticketmanager.batch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@EnableJpaAuditing
@EnableJpaRepositories("com.guardjo.ticketmanager.batch.repository")
@EnableTransactionManagement
@EntityScan("com.guardjo.ticketmanager.batch.domain")
public class TestBatchConfig {
}
