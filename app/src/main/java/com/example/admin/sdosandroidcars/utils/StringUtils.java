package com.example.admin.sdosandroidcars.utils;

public class StringUtils {
    public static String titleCase(String in) {
        return Character.toUpperCase(in.charAt(0)) + in.substring(1);
    }
}
