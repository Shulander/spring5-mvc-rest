package us.vicentini.controllers;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import us.vicentini.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorMessage error = ErrorMessage.build(ex.getMessage());
        return buildResponseEntityFromErrorMessage(error, HttpStatus.NOT_FOUND);
    }


    private ResponseEntity<ErrorMessage> buildResponseEntityFromErrorMessage(ErrorMessage error, HttpStatus status) {
        return new ResponseEntity<>(error, status);
    }


    @Builder
    @Getter
    private static class ErrorMessage {
        private final String message;


        private static ErrorMessage build(String message) {
            return new ErrorMessage(message);
        }
    }
}
