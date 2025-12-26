package com.example.socialnetwork.infrastructure.persistence.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import support.IntegrationTestBase;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class LiquibasePostExecutionTest extends IntegrationTestBase {

    @Autowired
    JdbcTemplate jdbc;

    @Test
    void liquibase_should_have_created_posts_table(){
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'posts'",
                Integer.class
        );
        assertThat(count).isEqualTo(1);
    }

}
