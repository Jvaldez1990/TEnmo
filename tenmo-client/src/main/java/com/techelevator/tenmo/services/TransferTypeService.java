package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferType;

public interface TransferTypeService {
    TransferType getTransferTypeByTransferTypeId(AuthenticatedUser authenticatedUser, int transferTypeId);

    TransferType getTransferTypeFromDescription(AuthenticatedUser authenticatedUser, String desc);
}
