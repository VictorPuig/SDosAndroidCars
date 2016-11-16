package com.example.admin.sdosandroidcars.info;


import android.util.Log;

import com.example.admin.sdosandroidcars.Constants;
import com.example.admin.sdosandroidcars.api.APICall;
import com.example.admin.sdosandroidcars.api.APICallbackListener;

import org.json.JSONObject;

public class Info implements APICallbackListener {

    public final static String TAG = "Info";
    public final static String INFO_URL = Constants.getUrlFor("getInfo");
    private InfoResultListener infoResultListener;

    public Info() {

    }

    public void doGetInfo() {
        Log.d(TAG, "doSignup started");

        APICall signupCall = new APICall(INFO_URL);

        //this (Login) sera a qui crida APICall al acabar una peticio
        signupCall.setOnAPICallbackListener(this);
        signupCall.doAPICall();
    }

    public void setOnInfoResultListener(InfoResultListener irs) {
        infoResultListener = irs;
    }


    @Override
    public void onAPICallback(JSONObject json) {
        Log.d(TAG, "onAPICallback cridat");

        infoResultListener.onInfoResult(json);
    }
}
