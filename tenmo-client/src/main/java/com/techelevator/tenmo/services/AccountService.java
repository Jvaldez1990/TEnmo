package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;

import java.math.BigDecimal;

public interface AccountService {

    BigDecimal getBalance(AuthenticatedUser authenticatedUser);

    Account getAccountByUserId(AuthenticatedUser authenticatedUser, int userId);

    Account getAccountById(AuthenticatedUser authenticatedUser, int accountId);

}
