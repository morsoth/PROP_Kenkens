package edu.upc.prop.clusterxx.exceptions;

public class InvalidKenkenSizeException extends RuntimeException {
    public InvalidKenkenSizeException() {
        super("Requested Kenken size is too small or too big.");
    }
}
