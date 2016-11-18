package com.example.admin.sdosandroidcars.api.info;


import android.util.Log;

import com.example.admin.sdosandroidcars.Constants;
import com.example.admin.sdosandroidcars.api.APICall;
import com.example.admin.sdosandroidcars.api.APICallbackListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class Info {

    public final static String TAG = "Info";
    public final static String INFO_URL = Constants.getUrlFor("getInfo");
    private InfoResultListener infoResultListener;

    public Info() {

    }

    public void doGetInfo() throws Exception {
        Log.d(TAG, "doSignup started");

        if (infoResultListener == null) {
            Log.d(TAG, "infoResultListener no establert");

            throw new Exception("infoResultListener no establert");
        }

        APICall signupCall = new APICall(INFO_URL);

        //this (Login) sera a qui crida APICall al acabar una peticio
        signupCall.setOnAPICallbackListener(new APICallbackListener() {
            @Override
            public void onAPICallback(JSONObject json) {
                Log.d(TAG, "onAPICallback cridat");

                Filter filter = new Filter();

                JSONArray colors = json.optJSONArray("color");
                for (int i = 0; i < colors.length(); i++) {
                    JSONObject colorObj = colors.optJSONObject(i);

                    if (colorObj == null) continue;

                    int colorId = colorObj.optInt("id");
                    String colorName = colorObj.optString("name");
                    Element colorEl = new Element(colorId, colorName);
                    filter.addColor(colorEl);
                }

                JSONArray makers = json.optJSONArray("maker");
                for (int i = 0; i < makers.length(); i++) {
                    JSONObject makerObj = makers.optJSONObject(i);

                    if (makerObj == null) continue;

                    int colorId = makerObj.optInt("id");
                    String makerName = makerObj.optString("name");
                    Element makerEl = new Element(colorId, makerName);
                    filter.addMaker(makerEl);
                }

                infoResultListener.onInfoResult(filter.getJSONObject());
            }
        });
        signupCall.doAPICall();
    }

    public void setOnInfoResultListener(InfoResultListener irs) {
        infoResultListener = irs;
    }
}
