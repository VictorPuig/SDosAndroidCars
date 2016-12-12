package com.example.admin.sdosandroidcars;


public final class Constants {
    public static final int API_TIMEOUT_MILIS = 1000 * 5;  // 5 seconds
    public static final String API_HOST = "192.168.1.84:8080";  // node ip

    public static String getUrlFor(String endpoint) {
        return "http://" + API_HOST + "/" + endpoint;
    }
}
