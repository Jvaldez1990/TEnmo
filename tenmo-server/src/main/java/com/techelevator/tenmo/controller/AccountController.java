package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    @Autowired
    AccountDao accountDao;

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {
        System.out.println(principal.getName());
        return accountDao.getBalance(principal.getName());
    }

    @RequestMapping(path="/account/user/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable int id) {
        return accountDao.getAccountByUserId(id);
    }

    @RequestMapping(path="/account/{id}", method = RequestMethod.GET)
    public Account getAccountFromAccountId(@PathVariable int id) {
        return accountDao.getAccountByAccountId(id);
    }

    @RequestMapping(path = "/account/{id}", method = RequestMethod.PUT)
    public void updateAccount(@Valid @RequestBody Account account) {
        accountDao.updateAccount(account);
    }
}
