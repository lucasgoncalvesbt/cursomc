package com.lucasg.cursomc.config;

import com.lucasg.cursomc.services.DBService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevConfig {

    private final DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public Boolean instantiateDatabase() throws ParseException {
        if (!"create".equals(strategy)) {
            return false;
        }
        dbService.instantiateTestDatabase();
        return true;
    }
}
