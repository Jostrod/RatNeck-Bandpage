package no.ratneck.backend.exception;



public class ConcertNotFoundException extends RuntimeException{
    public ConcertNotFoundException(String message){
        super(message);
    }
}
