package edu.upc.prop.clusterxx.exceptions;

public class InvalidOperationIdException extends RuntimeException {
    public InvalidOperationIdException(int op) {
        super("Operation id " + op + " is not valid.");
    }
}