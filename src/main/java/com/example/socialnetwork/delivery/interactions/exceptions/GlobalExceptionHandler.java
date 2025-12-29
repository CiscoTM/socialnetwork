package com.example.socialnetwork.delivery.interactions.exceptions;

import com.example.socialnetwork.domain.interactions.comment.exceptions.InvalidCommentContentException;
import com.example.socialnetwork.domain.interactions.follow.exceptions.DuplicateFollowException;
import com.example.socialnetwork.domain.interactions.follow.exceptions.SelfFollowNotAllowedException;
import com.example.socialnetwork.domain.interactions.like.exceptions.DuplicateLikeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateLikeException.class)
    public ResponseEntity<String> handleDuplicateLike(DuplicateLikeException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(SelfFollowNotAllowedException.class)
    public ResponseEntity<String> handleSelfFollow(SelfFollowNotAllowedException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(DuplicateFollowException.class)
    public ResponseEntity<String> handleDuplicateFollow(DuplicateFollowException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(InvalidCommentContentException.class)
    public ResponseEntity<String> handleInvalidCommentContent(InvalidCommentContentException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
