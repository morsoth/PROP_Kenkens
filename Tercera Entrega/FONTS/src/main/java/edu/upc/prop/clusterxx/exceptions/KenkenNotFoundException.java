package edu.upc.prop.clusterxx.exceptions;

public class KenkenNotFoundException extends RuntimeException {
    public KenkenNotFoundException() {
        super("Kenken not found.");
    }
}