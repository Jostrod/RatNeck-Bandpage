package no.ratneck.backend.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String resourceName, int requested, int remaining) {
        super("Not enough items to complete order. " + requested + " " + resourceName +
                " requested, " + remaining + " remaining.");
    }
}
