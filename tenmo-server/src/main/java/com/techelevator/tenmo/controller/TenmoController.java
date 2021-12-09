package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TenmoController {

    @Autowired
    AccountDao accountDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TransferDao transferDao;
    @Autowired
    TransferStatusDao transferStatusDao;
    @Autowired
    TransferTypeDao transferTypeDao;

    /*====================================================================
                        * Account Methods *
     ====================================================================*/

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {
        System.out.println(principal.getName());
        return accountDao.getBalance(userDao.findByUsername(principal.getName()).getId());
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
    public void updateAccount(@Valid @RequestBody Account account, int id) throws AccountNotFoundException {
        accountDao.updateAccount(account, id);
    }

    /*====================================================================
                        * User Methods *
     ====================================================================*/

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userDao.listAll();
    }

    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET)
    public User findByUsername (@RequestBody String username) {
        return userDao.findByUsername(username);
    }

    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET)
    public int findIdByUsername (@RequestBody String username) {
        return userDao.findIdByUsername(username);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public boolean createUser (String username, String password) {
        return userDao.createUser(username, password);
    }

    @RequestMapping(path="/users/{id}", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable int id) {
        return userDao.getUserByUserId(id);
    }

    /*====================================================================
                        * Transfer Methods *
     ====================================================================*/

    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }

    @RequestMapping(path = "/transfers/{userId}", method = RequestMethod.GET)
    public List<Transfer> getTransfersByUserId(int userId) {
        return transferDao.getTransfersByUserId(userId);
    }

    @RequestMapping(path = "/transfers/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(int transferId) {
        return transferDao.getTransferByTransferId(transferId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public void createTransfer (Transfer transfer) {
        transferDao.createTransfer(transfer);
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.PUT)
    public void updateTransfer(@Valid @RequestBody Transfer transfer) {
        transferDao.updateTransfer(transfer);
    }

     /*====================================================================
                        * Transfer Status Method *
     ====================================================================*/

    @RequestMapping(path = "/transferStatus/{transferStatusId}", method = RequestMethod.GET)
    public TransferStatus getTransferStatusById(int transferStatusId) {
        return transferStatusDao.getTransferStatusById(transferStatusId);
    }

    @RequestMapping(path = "/transferStatus/{transferStatusByDesc}", method = RequestMethod.GET)
    public TransferStatus getTransferStatusByDescription(String description) {
        return transferStatusDao.getTransferStatusByDescription(description);
    }

    /*====================================================================
                        * Transfer Type Method *
     ====================================================================*/

    @RequestMapping(path = "/transferType/{transferTypeId}", method = RequestMethod.GET)
    public TransferType getTransferTypeByTransferTypeId(int transferTypeId) {
        return transferTypeDao.getTransferTypeByTransferTypeId(transferTypeId);
    }

    @RequestMapping(path = "/transferType/{transferTypeByDesc}", method = RequestMethod.GET)
    public TransferType getTransferTypeFromDescription(String description) {
        return transferTypeDao.getTransferTypeFromDescription(description);
    }
}
