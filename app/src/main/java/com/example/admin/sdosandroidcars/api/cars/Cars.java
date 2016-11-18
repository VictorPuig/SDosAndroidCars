package com.example.admin.sdosandroidcars.api.cars;


import android.util.Log;

import com.example.admin.sdosandroidcars.Constants;
import com.example.admin.sdosandroidcars.api.APICall;
import com.example.admin.sdosandroidcars.api.APICallbackListener;

import org.json.JSONObject;

public class Cars {

    private final static String TAG = "Cars";
    private final static String GET_CARS_URL = Constants.getUrlFor("getCars");
    private final static String MAKER_URL = Constants.getUrlFor("addMaker");
    private final static String COLOR_URL = Constants.getUrlFor("addColor");
    private final static String CAR_URL = Constants.getUrlFor("addCar");

    Cars() {}

    public static void doGetCars(JSONObject filter, final CarsResultListener crs) throws Exception {
        Log.d(TAG, "doGetCars() cridat");

        APICall getCarsCall = new APICall(GET_CARS_URL);

        getCarsCall.setOnAPICallbackListener(new APICallbackListener() {
            @Override
            public void onAPICallback(JSONObject json) {
                Log.d(TAG, "doGetCars:onAPICallback cridat");
                crs.onCarsResult(json);
            }
        });
        getCarsCall.doAPICall();
    }

    public static void doAddMaker(JSONObject makerJson, final CarsResultListener crs) throws Exception {
        Log.d(TAG, "doAddMaker started");

        APICall addMakerCall = new APICall("POST", MAKER_URL);

        addMakerCall.setOnAPICallbackListener(new APICallbackListener() {
            @Override
            public void onAPICallback(JSONObject json) {
                Log.d(TAG, "doAddMaker:onAPICallback cridat");
                crs.onCarsResult(json);
            }
        });
        addMakerCall.doAPICall();
    }

    public static void doAddColor(JSONObject colorJson, final CarsResultListener crs) throws Exception {
        Log.d(TAG, "doAddColor started");

        APICall addMakerCall = new APICall("POST", COLOR_URL);

        addMakerCall.setOnAPICallbackListener(new APICallbackListener() {
            @Override
            public void onAPICallback(JSONObject json) {
                Log.d(TAG, "doAddColor:onAPICallback cridat");
                crs.onCarsResult(json);
            }
        });
        addMakerCall.doAPICall();
    }

    public static void doAddCar(JSONObject carJson, final CarsResultListener crs) throws Exception {
        Log.d(TAG, "doAddCar started");

        APICall addMakerCall = new APICall("POST", CAR_URL);

        addMakerCall.setOnAPICallbackListener(new APICallbackListener() {
            @Override
            public void onAPICallback(JSONObject json) {
                Log.d(TAG, "doAddCar:onAPICallback cridat");
                crs.onCarsResult(json);
            }
        });
        addMakerCall.doAPICall();
    }
}
