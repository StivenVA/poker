package org.example.poker.util;

import org.example.poker.exceptions.GameException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandler {

    public void handleException(Exception e) {
         switch (e.getClass().getSimpleName()) {
             case "NotFoundException":
                throw new GameException(e.getMessage(), HttpStatus.NOT_FOUND);
             case "JugadoresCompletosException":
                throw new GameException(e.getMessage(),HttpStatus.UNAUTHORIZED );
                case "IllegalArgumentException":
                throw new GameException(e.getMessage(), HttpStatus.BAD_REQUEST);
                default:
                throw  new GameException("An error occurred", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
