package edu.upc.prop.clusterxx.exceptions;

public class CantCreateKenkenException extends RuntimeException {
    public CantCreateKenkenException() {
        super("Can't create Kenken.");
    }
}