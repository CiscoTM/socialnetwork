package com.example.socialnetwork.domain.interactions.comment;

import com.example.socialnetwork.domain.interactions.comment.exceptions.InvalidCommentContentException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class CommentContentTest {
    @Test
    void valid_content_is_accepted(){
        CommentContent content = CommentContent.of("Hello");
        assertThat(content.value()).isEqualTo("Hello");
    }
    @Test
    void empty_content_throws_exception(){
        assertThatThrownBy(() -> CommentContent.of(""))
                .isInstanceOf(InvalidCommentContentException.class);
    }
    @Test
    void too_long_content_throws_exception(){
        String longText = "a".repeat(501);
        assertThatThrownBy(() -> CommentContent.of(longText))
                .isInstanceOf(InvalidCommentContentException.class);
    }
}
