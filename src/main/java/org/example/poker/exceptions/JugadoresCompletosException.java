package org.example.poker.exceptions;

public class JugadoresCompletosException extends Exception{

    public JugadoresCompletosException(String message){
        super(message);
    }

    public JugadoresCompletosException(String message, Throwable cause){
        super(message, cause);
    }
}
