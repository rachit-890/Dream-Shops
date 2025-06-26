package org.dailycodework.dreamshops.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException; // Correct import
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class) // Now catches Spring's exception
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        String message = "You don't have permission to this action";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}