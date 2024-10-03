package edu.upc.prop.clusterxx.exceptions;

public class NullKenkenException extends Exception {
    public NullKenkenException() {
        super("Kenken reference is null.");
    }
}