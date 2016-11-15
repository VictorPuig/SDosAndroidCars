package com.example.admin.sdosandroidcars;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class Login implements APICallCallback {

    public static final String TAG = "Login";
    public static final String LOGIN_URL = Constants.getUrlFor("login");

    String username, password;

    Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void doLogin() throws IOException {
        Log.d(TAG, "doLogin started");

        APICall loginCall = new APICall("POST", LOGIN_URL);

        JSONObject outputJson = new JSONObject();
        try {
            outputJson.put("username", username);
            outputJson.put("password", password);
        } catch (JSONException e) {
            Log.e(TAG, "Json error mentre es construia el l'objecte per enviar");
            e.printStackTrace();
        }

        loginCall.setRequestJson(outputJson);
        //this (Login) sera a qui crida APICall al acabar una peticio
        loginCall.setCallbackClass(this);
        loginCall.doAPICall();
    }

    @Override
    public void APICallCallback(JSONObject json) {
        Log.d(TAG, "Override this method!");
        Log.d(TAG, "APICallResult:" + json.toString());
    }
}
