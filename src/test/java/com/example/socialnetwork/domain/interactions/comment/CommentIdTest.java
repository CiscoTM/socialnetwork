package com.example.socialnetwork.domain.interactions.comment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class CommentIdTest {
    @Test
    void generate_creates_valid_id(){
        CommentId commentId = CommentId.generate();
        assertThat(commentId.value()).isNotNull();
    }
    @Test
    void null_value_throws_exception(){
        assertThatThrownBy(() -> CommentId.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
