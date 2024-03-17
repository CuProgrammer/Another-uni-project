package com.blackbank.flyingdollar;

public class Cheque {
    String senderUsername;
    String recieverUsername;
    double amount;
    ChequeStatus status;
    int yyyy;
    int mm;
    int dd;
    int chequeId;

    @Override
    public boolean equals(Object comparedObject)
    {
        if (comparedObject == this)
            return true;
        if (!(comparedObject instanceof Cheque) || comparedObject == null)
            return false;
        return this.chequeId == ((Cheque) comparedObject).chequeId;
    }
}
