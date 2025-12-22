package com.example.socialnetwork.domain.post;

import com.example.socialnetwork.domain.post.exception.InvalidPostContentException;
import com.example.socialnetwork.domain.post.exception.NullPostContentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PostContentTest {
    @Test
    void shouldCreateValidPostContent(){
        PostContent content = PostContent.of("Hello world");
        assertEquals("Hello world", content.value());
    }
    @Test
    void shouldTrimContent(){
        PostContent content = PostContent.of(" Hello ");
        assertEquals("Hello", content.value());
    }
    @Test
    void shouldThrowExceptionWhenContentIsNull(){
        assertThrows(NullPostContentException.class, () -> PostContent.of(null));
    }
    @Test
    void shouldThrowExceptionWhenContentIsEmpty(){
        assertThrows(InvalidPostContentException.class, () -> PostContent.of("   "));
    }
}
