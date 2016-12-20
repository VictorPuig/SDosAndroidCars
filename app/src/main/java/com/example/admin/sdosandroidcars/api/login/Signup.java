package com.example.admin.sdosandroidcars.api.login;


import android.util.Log;

import com.example.admin.sdosandroidcars.Constants;
import com.example.admin.sdosandroidcars.api.APICall;
import com.example.admin.sdosandroidcars.api.APICallbackListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Signup implements APICallbackListener {

    public final static String TAG = "Signup";
    private final static String SIGNUP_URL = Constants.getUrlFor("signup");

    private String username, password;
    private SignupResultListener signupResultListener;

    public Signup(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void doSignup() throws Exception {
        Log.d(TAG, "doSignup started");

        if (signupResultListener == null) {
            Log.d(TAG, "signupResultListener no establert");

            throw new Exception("signupResultListener no establert");
        }

        APICall signupCall = new APICall("POST", SIGNUP_URL);

        JSONObject outputJson = new JSONObject();
        try {
            outputJson.put("username", username);
            outputJson.put("password", password);
        } catch (JSONException e) {
            Log.e(TAG, "Json error mentre es construia el l'objecte per enviar");
            e.printStackTrace();
        }

        signupCall.setRequestJson(outputJson);
        signupCall.setOnAPICallbackListener(this);
        signupCall.doAPICall();
    }

    public void setOnSignupResultListener(SignupResultListener srl) {
        signupResultListener = srl;
    }

    @Override
    public void onAPICallback(JSONObject json) {
        Log.d(TAG, "onAPICallback cridat");

        signupResultListener.onSignupResult(json);
    }
}
