package com.blackbank.flyingdollar;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

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
    
    public static void parseMap(Object obj, HashMap<String, String> info)
    {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field:fields) {
            field.setAccessible(true);
            try {
                if (field.getType().equals(int.class)) {
                    field.set(obj, Integer.valueOf(info.get(field.getName())));
                } else if (field.getType().equals(long.class)) {
                    field.set(obj, Long.valueOf(info.get(field.getName())));
                } else if (field.getType().equals(double.class)) {
                    field.set(obj, Double.valueOf(info.get(field.getName()))); 
                } else if (field.getType().equals(short.class)) {
                    field.set(obj, Short.valueOf(info.get(field.getName())));
                } else if (field.getType().equals(float.class)) {
                    field.set(obj, Float.valueOf(info.get(field.getName())));
                } else if (field.getType().equals(byte.class)) {
                    field.set(obj, Byte.valueOf(info.get(field.getName()))); 
                } else if (field.getType().equals(char.class)) {
                    field.set(obj, info.get(field.getName()).charAt(0)); 
                } else if (field.getType().equals(String.class)) {
                    field.set(obj, info.get(field.getName()));
                } else {
                    parseString(field.get(obj), info.get(field.getName()));
                }
            } catch (Exception e) {
                    e.printStackTrace();
            }
        }
    }

    public static void parseString(Object obj, String parsable)
    {
        DataReader dataReader = new DataReader(parsable);
        HashMap<String, String> info = dataReader.scanMap();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field:fields) {
            field.setAccessible(true);
            try {
                if (field.getType().equals(int.class)) {
                    field.set(obj, Integer.valueOf(info.get(field.getName())));
                } else if (field.getType().equals(long.class)) {
                    field.set(obj, Long.valueOf(info.get(field.getName())));
                } else if (field.getType().equals(double.class)) {
                    field.set(obj, Double.valueOf(info.get(field.getName()))); 
                } else if (field.getType().equals(short.class)) {
                    field.set(obj, Short.valueOf(info.get(field.getName())));
                } else if (field.getType().equals(float.class)) {
                    field.set(obj, Float.valueOf(info.get(field.getName())));
                } else if (field.getType().equals(byte.class)) {
                    field.set(obj, Byte.valueOf(info.get(field.getName()))); 
                } else if (field.getType().equals(char.class)) {
                    field.set(obj, info.get(field.getName()).charAt(0)); 
                } else if (field.getType().equals(String.class)) {
                    field.set(obj, info.get(field.getName()));
                } else if (field.getType().equals(Gender.class)) {
                    field.set(obj, Gender.valueOf(info.get(field.getName())));
                } else if (field.getType().equals(ClientType.class)) {
                    field.set(obj, ClientType.valueOf(info.get(field.getName())));
                } else if (field.getType().equals(ClientStatus.class)) {
                    field.set(obj, ClientStatus.valueOf(info.get(field.getName())));
                } else {
                    parseString(field.get(obj), info.get(field.getName()));
                }
            } catch (Exception e) {
                    e.printStackTrace();
            }
        }
    }

    public static String makeParsable(Object obj)
    {
        Field[] fields = obj.getClass().getDeclaredFields();
        String parsable = "{\n";
        for (Field field:fields) {
            field.setAccessible(true);
            parsable += "\t\"" + field.getName() + "\":\"";
            try {
                if (field.getType().isPrimitive() || field.getType().isEnum()) { /* char should be checked if it's ", fix later */
                    parsable += field.get(obj).toString();
                } else if (field.getType().equals(String.class)) {
                    for (int i = 0; i < ((String) field.get(obj)).length(); i++) {
                        char c = ((String) field.get(obj)).charAt(i);
                        parsable += c != '\"' ? "" + c : "\\\"";
                    }
                } else {
                    String subParsable = makeParsable(field.get(obj));
                    for (int i = 0; i < subParsable.length(); i++) {
                        char c = subParsable.charAt(i);
                        parsable += c != '\"' ? "" + c : "\\\"";
                    }
                }
            } catch (Exception e) {
                    e.printStackTrace();
            }
            parsable += "\";\n";
        }
        return parsable + "}";
    }
}
