package io.github.guardjo.ticketmanager.batch.config;

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
@EnableJpaRepositories("io.github.guardjo.ticketmanager.batch.repository")
@EnableTransactionManagement
@EntityScan("io.github.guardjo.ticketmanager.batch.domain")
public class TestBatchConfig {
}
