package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;

import java.util.List;

public interface TransferStatusDao {

    TransferStatus getTransferStatusByTransferStatusId(int transferStatusId);

    List<TransferStatus> getAllTransferStatuses();

    void createTransferStatus(TransferStatus transferStatuses);

}
