package com.techelevator.tenmo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Not enough money")
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("Not enough money.");}
}
