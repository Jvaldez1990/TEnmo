package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalance(String user);
    Account getAccountByUserId(int userId);
    Account getAccountByAccountId(int accountId);

    void updateAccount(Account accountToUpdate);
}
