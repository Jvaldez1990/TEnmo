package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> getTransfersByUserId(int userId);

    Transfer getTransferByTransferId(int transferId);

    List<Transfer> getAllTransfers();

    List<Transfer> getPendingTransfers(int userId);

    void createTransfer(Transfer transfer);

    void updateTransfer(Transfer transfer);

}
