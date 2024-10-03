package edu.upc.prop.clusterxx.exceptions;

public class InvalidGameStateException extends RuntimeException {
    public InvalidGameStateException(String currentState, String expectedState) {
        super("Kenken in state" + currentState + ". Expected to be " + expectedState + ".");
    }
}