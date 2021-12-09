package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;

import java.util.List;

public interface TransferTypeDao {
    TransferType getTransferTypeByTransferTypeId(int transferTypeId);

    TransferType getTransferTypeFromDescription(String desc);

}
