package com.example.admin.sdosandroidcars.login;


import android.util.Log;

import com.example.admin.sdosandroidcars.Constants;
import com.example.admin.sdosandroidcars.api.APICall;
import com.example.admin.sdosandroidcars.api.OnAPICallbackListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Signup implements OnAPICallbackListener {

    public final static String TAG = "Signup";
    public final static String SIGNUP_URL = Constants.getUrlFor("signup");

    String username, password;
    SignupResultListener signupResultListener;

    public Signup(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void doSignup() {
        Log.d(TAG, "doSignup started");

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
        //this (Login) sera a qui crida APICall al acabar una peticio
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
