package org.example.poker.controller.response;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class CustomResponse<T> extends ResponseEntity<T> {

    private String message;

    public CustomResponse(T body, HttpStatusCode httpStatusCode,String message) {
        super(body, httpStatusCode);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
