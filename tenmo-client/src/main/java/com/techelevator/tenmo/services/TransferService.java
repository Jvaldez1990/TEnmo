package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferService {
    Transfer[] getTransfersByUserId(AuthenticatedUser authenticatedUser, int userId);

    Transfer getTransferByTransferId(AuthenticatedUser authenticatedUser, int transferId);

    Transfer[] getAllTransfers(AuthenticatedUser authenticatedUser);

    Transfer[] getPendingTransfers(AuthenticatedUser authenticatedUser, int userId);

    void createTransfer(AuthenticatedUser authenticatedUser, Transfer transfer);

    void updateTransfer(AuthenticatedUser authenticatedUser, Transfer transfer);
}
