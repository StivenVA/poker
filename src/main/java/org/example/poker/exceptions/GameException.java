package org.example.poker.exceptions;

import org.springframework.http.HttpStatus;

public class GameException extends RuntimeException{

    private HttpStatus httpStatus;

    public GameException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
