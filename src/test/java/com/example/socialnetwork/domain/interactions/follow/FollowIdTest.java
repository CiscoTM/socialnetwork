package com.example.socialnetwork.domain.interactions.follow;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class FollowIdTest {

    @Test
    void generate_creates_valid_id(){
        FollowId followId = FollowId.generate();
        assertThat(followId.value()).isNotNull();
    }
    @Test
    void null_value_throws_exception(){
        assertThatThrownBy(() -> FollowId.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
