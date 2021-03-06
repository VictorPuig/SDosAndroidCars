package com.example.admin.sdosandroidcars.api.login;

import android.util.Log;

import com.example.admin.sdosandroidcars.Constants;
import com.example.admin.sdosandroidcars.api.APICall;
import com.example.admin.sdosandroidcars.api.APICallbackListener;

import org.json.JSONException;
import org.json.JSONObject;


public class Login implements APICallbackListener {

    public static final String TAG = "Login";
    private static final String LOGIN_URL = Constants.getUrlFor("login");

    private String username, password;
    private LoginResultListener loginResultListener;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void doLogin() throws Exception {
        Log.d(TAG, "doLogin started");

        if (loginResultListener == null) {
            Log.d(TAG, "loginResultListener no establert");

            throw new Exception("loginResultListener no establert");
        }

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
        loginCall.setOnAPICallbackListener(this);
        loginCall.doAPICall();
    }

    public void setOnLoginResultListener(LoginResultListener lrs) {
        loginResultListener = lrs;
    }

    @Override
    public void onAPICallback(JSONObject json) {
        Log.d(TAG, ".onAPICallback() cridat");

        loginResultListener.onLoginResult(json);
    }
}
