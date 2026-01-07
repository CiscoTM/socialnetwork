package com.example.socialnetwork;

import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestLiquibaseConfig {

    @Bean
    @Primary
    public LiquibaseProperties liquibaseProperties() {
        LiquibaseProperties props = new LiquibaseProperties();
        props.setEnabled(true);
        props.setChangeLog("classpath:/db/changelog/db.changelog-master.yaml");
        return props;
    }
}

