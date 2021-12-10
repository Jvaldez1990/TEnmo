package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;

    public Transfer(int transferId, int transferTypeId, int transferStatusId, int accountFrom,
                    int accountTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountTo = accountTo;
        this.accountFrom = accountFrom;
        this.amount = amount;
    }

    public Transfer() {

    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void displayTransfers(Transfer[] transfers, User accountFrom, User accountTo) {

        System.out.println("-----------------------------------");
        System.out.println("Transfers");
        System.out.println("ID          From/To          Amount");
        System.out.println("-----------------------------------");

        for(Transfer transfer : transfers) {
            System.out.println(transferId + "From: " + accountFrom.getUsername() +  "        $ " + transfer.getAmount());
            System.out.println(transferId + "To: " + accountTo.getUsername() +  "        $ " + transfer.getAmount());
        }

        System.out.println("---------");
    }
}
