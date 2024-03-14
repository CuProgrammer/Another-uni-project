package com.blackbank.flyingdollar;

import static com.blackbank.flyingdollar.ClientStatus.NORMAL;
import static com.blackbank.flyingdollar.ClientType.ACCOUNT;
import static com.blackbank.flyingdollar.Gender.MALE;

class Cat {
    int age;
    String name;

    public Cat()
    {
        age = 0;
        name = "";
    }

    public Cat(int age, String name)
    {
        this.age = age;
        this.name = name;
    }

    public String toString()
    {
        return "age: " + age + "\tname: " + name;
    }
}

class UltraCat {
    Cat cat;
    int number;

    public UltraCat()
    {
        this.cat = new Cat();
    }

    public UltraCat(int age, String name, int number)
    {
        cat = new Cat(age, name);
        this.number = number;
    }

    public String toString()
    {
        return cat.toString() + "\tnumber: " + number;
    }
}

public class FlyingDollar {

    public static void main(String[] args) {
        Client account = new Client("user", "pass", "name", MALE, ACCOUNT, NORMAL);
        System.out.println(account);
        String parsable = BankUtils.makeParsable(account);
        System.out.println(parsable);
        Client parsed = new Client();
        BankUtils.parseString(parsed, parsable);
        System.out.println(parsed);
    }
}
