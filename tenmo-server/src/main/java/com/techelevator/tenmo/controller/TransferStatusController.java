package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@PreAuthorize("isAuthenticated()")
public class TransferStatusController {
    @Autowired
    TransferStatusDao transferStatusDao;

    @RequestMapping(path = "/transferstatus/{transferStatusId}", method = RequestMethod.GET)
    public TransferStatus getTransferStatusById(@PathVariable int transferStatusId) {
        return transferStatusDao.getTransferStatusById(transferStatusId);
    }

    @RequestMapping(path = "/transferstatus/filter", method = RequestMethod.GET)
    public TransferStatus getTransferStatusByDescription(@RequestParam String description) {
        return transferStatusDao.getTransferStatusByDesc(description);
    }
}
