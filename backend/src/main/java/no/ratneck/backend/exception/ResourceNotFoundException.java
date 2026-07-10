package no.ratneck.backend.exception;


public class ResourceNotFoundException extends RuntimeException{


    public ResourceNotFoundException(String resourceName, Long id){
        super("No " + resourceName + " found with ID " + id);

    }
}
