package ru.liga.loadersystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}