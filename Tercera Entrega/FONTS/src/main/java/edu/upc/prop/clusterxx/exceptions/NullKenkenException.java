package edu.upc.prop.clusterxx.exceptions;

public class NullKenkenException extends RuntimeException {
    public NullKenkenException() {
        super("Kenken reference is null.");
    }
}