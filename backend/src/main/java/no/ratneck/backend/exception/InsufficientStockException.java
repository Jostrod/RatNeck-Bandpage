package no.ratneck.backend.exception;

import no.ratneck.backend.common.MerchType;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(MerchType resourceName, int requested, int remaining) {
        super("Not enough items to complete order. " + requested + " " + resourceName +
                " requested, " + remaining + " remaining.");
    }
}
