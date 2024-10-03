package edu.upc.prop.clusterxx.exceptions;

public class CantCreateUserException extends RuntimeException {
    public CantCreateUserException(String name) {
        super("Can't create user " + name + ".");
    }
}