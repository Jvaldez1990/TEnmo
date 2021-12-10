package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferTypeDao;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferTypeController {
    @Autowired
    TransferTypeDao transferTypeDao;

    @RequestMapping(path = "/transfertype/{transferTypeId}", method = RequestMethod.GET)
    public TransferType getTransferTypeByTransferTypeId(@PathVariable int transferTypeId) {
        return transferTypeDao.getTransferTypeByTransferTypeId(transferTypeId);
    }

    @RequestMapping(path = "/transfertype/filter", method = RequestMethod.GET)
    public TransferType getTransferTypeFromDescription(@RequestParam String description) {
        return transferTypeDao.getTransferTypeFromDescription(description);
    }
}
