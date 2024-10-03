package edu.upc.prop.clusterxx.exceptions;

public class InvalidOperationIdException extends Exception {
    public InvalidOperationIdException(int op) {
        super("Operation id " + op + " is not valid.");
    }
}