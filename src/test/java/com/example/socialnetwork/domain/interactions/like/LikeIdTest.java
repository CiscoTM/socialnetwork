package com.example.socialnetwork.domain.interactions.like;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class LikeIdTest {
    @Test
    void generate_creates_valid_id(){
        LikeId likeId = LikeId.generate();
        assertThat(likeId.value()).isNotNull();
    }
    @Test
    void null_value_throws_exception(){
        assertThatThrownBy(() -> LikeId.of(null) )
                .isInstanceOf(IllegalArgumentException.class);
    }
}
