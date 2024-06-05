package org.example.poker.controller;

import org.example.poker.controller.response.CustomResponse;
import org.example.poker.exceptions.GameException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(GameException.class)
    public ResponseEntity<?> handleException(GameException e) {
        return new CustomResponse<>(e.getMessage(), e.getHttpStatus(), e.getMessage());
    }

}
