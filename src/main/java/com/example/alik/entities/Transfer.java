package com.example.alik.entities;

import java.sql.Timestamp;

public class Transfer {
    private int id;
    private User userFrom;
    private User userTo;
    private int transferBalance;
    private Timestamp transferDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public int getTransferBalance() {
        return transferBalance;
    }

    public void setTransferBalance(int transferBalance) {
        this.transferBalance = transferBalance;
    }

    public Timestamp getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Timestamp transferDate) {
        this.transferDate = transferDate;
    }
}
