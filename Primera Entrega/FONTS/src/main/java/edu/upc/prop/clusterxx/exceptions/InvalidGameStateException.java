package edu.upc.prop.clusterxx.exceptions;

public class InvalidGameStateException extends Exception {
    public InvalidGameStateException(String currentState, String expectedState) {
        super("Kenken in state" + currentState + ". Expected to be " + expectedState + ".");
    }
}