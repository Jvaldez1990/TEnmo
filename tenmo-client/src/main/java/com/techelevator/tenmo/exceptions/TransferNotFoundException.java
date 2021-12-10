package com.techelevator.tenmo.exceptions;

public class TransferNotFoundException extends Exception {
    public TransferNotFoundException() {
        super("Transfer not found, enter a valid transfer ID.");
    }
}
