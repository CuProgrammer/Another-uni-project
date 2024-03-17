package com.blackbank.flyingdollar;

import java.util.Objects;

import static com.blackbank.flyingdollar.Gender.*;
import static com.blackbank.flyingdollar.ClientType.*;
import static com.blackbank.flyingdollar.ClientStatus.*;
public class Client {
    String username;
    String password;
    String name;
    Gender gender;
    double balance;
    ClientType type;
    ClientStatus status;
    
    public Client(String username, String password, String name, Gender gender, ClientType type, ClientStatus status)
    {
        this.username = username;
        this.password = BankUtils.hashString(password);
        this.name = name;
        this.gender = gender;
        this.type = type;
        this.status = status;
    }

    public Client()
    {
        username = "";
        password = "";
        name = "";
        gender = MALE;
        type = ACCOUNT;
        status = NORMAL;
    }

    public boolean isDeleted()
    {
        return status == DELETE;
    }

    @Override
    public String toString()
    {
        return BankUtils.combine(username, password, name, gender, balance, type, status);
    }
    
    @Override
    public boolean equals(Object comparedObject)
    {
        if (this == comparedObject)
            return true;
        if (!(comparedObject instanceof Client) || comparedObject == null)
            return false;
        Client comparedClient = (Client) comparedObject;
        return comparedClient.username.equals(username);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.username);
        return hash;
    }
    
    public boolean confirmPassword(String password)
    {
        return this.password.equals(BankUtils.hashString(password));
    }
}