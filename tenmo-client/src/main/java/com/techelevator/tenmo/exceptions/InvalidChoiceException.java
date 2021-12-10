package com.techelevator.tenmo.exceptions;

public class InvalidChoiceException extends Exception {
    public InvalidChoiceException() {
        super("Cannot send money to yourself. Please choose a different user.");
    }
}
