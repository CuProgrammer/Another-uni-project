package com.blackbank.flyingdollar;

public  class BankUtils {
    public String combine(Object... objs)
    {
        String combination = "";
        for (int i = 0; i < objs.length; i++)
            combination += objs[i] + (i < objs.length - 1 ? "\t" : "");
        return combination;
    }
}
