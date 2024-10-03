package edu.upc.prop.clusterxx.exceptions;

public class UserNotFoundException extends RuntimeException {
public UserNotFoundException(String name) {
        super("User " + name + " not found.");
    }
}