package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatuses;
import com.techelevator.tenmo.model.TransferTypes;

import java.util.List;

public interface TransferTypesDao {
    TransferTypes getTransferStatusByTransferTypesId(int transferTypeId);

    List<TransferTypes> getAllTransferTypes();

    void createTransferTypes(TransferTypes transferTypes);

    void updateTransferTypes(TransferTypes transferTypes);
}
