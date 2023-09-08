package com.guardjo.ticketmanager.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {
    @Bean
    public SpringResourceTemplateResolver thymeleafResourceTemplateResolver(
            SpringResourceTemplateResolver resourceTemplateResolver, ThymeleafProperty thymeleafProperty) {
        resourceTemplateResolver.setUseDecoupledLogic(thymeleafProperty.decoupledLogic());

        return resourceTemplateResolver;
    }

    @ConfigurationProperties(prefix = "spring.thymeleaf3")
    public record ThymeleafProperty(boolean decoupledLogic) {};
}
