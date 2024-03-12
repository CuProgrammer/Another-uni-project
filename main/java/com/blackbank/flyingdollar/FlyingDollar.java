package com.blackbank.flyingdollar;

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
        UltraCat ultraCat = new UltraCat(400, "Satella", 7);
        UltraCat newUltraCat = new UltraCat();
        String parsable = BankUtils.makeParsable(ultraCat);
        BankUtils.parseString(newUltraCat, parsable);
        System.out.println(parsable);
        System.out.println(newUltraCat);
    }
}
