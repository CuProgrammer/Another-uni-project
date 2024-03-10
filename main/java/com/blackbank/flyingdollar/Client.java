package com.blackbank.flyingdollar;

import com.blackbank.flyingdollar.Gender.*;

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
}
