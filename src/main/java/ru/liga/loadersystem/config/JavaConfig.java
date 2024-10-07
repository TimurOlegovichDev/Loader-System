package ru.liga.loadersystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class JavaConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Map<String, Cargo> cargoRepository() {
        return new HashMap<>();
    }

    @Bean
    public Map<Transport, List<Cargo>> transportCargoRepository() {
        return new HashMap<>();
    }

}