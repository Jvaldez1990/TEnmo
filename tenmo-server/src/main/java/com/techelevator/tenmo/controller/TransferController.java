package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.InsufficientFundsException;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    @Autowired
    TransferDao transferDao;
    @Autowired
    AccountDao accountDao;

    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }

    @RequestMapping(path = "/transfers/{userId}", method = RequestMethod.GET)
    public List<Transfer> getTransfersByUserId(@PathVariable int userId) {
        return transferDao.getTransfersByUserId(userId);
    }

    @RequestMapping(path = "/transfers/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(@PathVariable int transferId) {
        return transferDao.getTransferByTransferId(transferId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.POST)
    public void createTransfer (@RequestBody Transfer transfer, @PathVariable int id) throws InsufficientFundsException {

        BigDecimal transferAmount = transfer.getAmount();
        Account fromAccount = accountDao.getAccountByAccountId(transfer.getAccountFrom());
        Account toAccount = accountDao.getAccountByAccountId(transfer.getAccountTo());

        fromAccount.sendMoney(transferAmount);
        toAccount.receiveMoney(transferAmount);

        transferDao.createTransfer(transfer);

        accountDao.updateAccount(fromAccount);
        accountDao.updateAccount(toAccount);
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.PUT)
    public void updateTransfer(@Valid @RequestBody Transfer transfer) {
        transferDao.updateTransfer(transfer);
    }
}
