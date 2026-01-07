package com.example.socialnetwork.infrastructure.persistence.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import support.IntegrationTestBase;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class LiquibasePostExecutionTest extends IntegrationTestBase {

    @Configuration
    @ImportAutoConfiguration({
            DataSourceAutoConfiguration.class,
            JdbcTemplateAutoConfiguration.class,
            LiquibaseAutoConfiguration.class
    })
    static class MinimalConfig {
        // No escanea tu aplicación
        // Solo activa la autoconfiguración necesaria
    }

    @Autowired
    JdbcTemplate jdbc;

    @Test
    void liquibase_should_have_created_posts_table() {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'posts'",
                Integer.class
        );
        assertThat(count).isEqualTo(1);
    }
}
