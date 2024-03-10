package com.blackbank.flyingdollar;

import com.blackbank.flyingdollar.Gender.*;
import java.util.Objects;

public class Client {
    private String username;
    private String password;
    private String name;
    private Gender gender;
    
    public Client(String username, String password, String name, Gender gender)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
    }

    @Override
    public String toString()
    {
        return BankUtils.combine(username, password, name, gender);
    }
    
    @Override
    public boolean equals(Object comparedObject)
    {
        if (this == comparedObject)
            return true;
        if (!(comparedObject instanceof Client))
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
}
