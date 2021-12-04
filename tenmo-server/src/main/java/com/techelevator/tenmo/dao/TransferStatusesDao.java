package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatuses;

import java.util.List;

public interface TransferStatusesDao {

    TransferStatuses getTransferStatusByTransferStatusId(int transferStatusId);

    List<TransferStatuses> getAllTransferStatuses();

    void createTransferStatus(TransferStatuses transferStatuses);

    void updateTransferStatus(TransferStatuses transferStatuses);
}
