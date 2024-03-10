package com.blackbank.flyingdollar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BankUtils {
    public static String combine(Object... objs)
    {
        String combination = "";
        for (int i = 0; i < objs.length; i++)
            combination += objs[i] + (i < objs.length - 1 ? "\t" : "");
        return combination;
    }
    
    public static String hashString(String input)
    {
        try {
            MessageDigest digest = MessageDigest.getInstance("sha256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() ==1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
