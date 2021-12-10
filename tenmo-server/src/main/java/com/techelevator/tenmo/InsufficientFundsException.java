package com.techelevator.tenmo;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("Not enough money.");}
}
