package com.example.socialnetwork.infrastructure.persistence.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import support.PostgresTestContainer;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.test.database.replace=NONE",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.liquibase.enabled=true"
})
class LiquibasePostExecutionTest extends PostgresTestContainer {

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
