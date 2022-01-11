package com.lucasg.cursomc.config;

import com.lucasg.cursomc.services.DBService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class TestConfig {

    private final DBService dbService;

    @Bean
    public Boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }
}
