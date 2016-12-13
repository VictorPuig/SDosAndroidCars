package com.example.admin.sdosandroidcars.api.cars;


import android.util.Log;

import com.example.admin.sdosandroidcars.Constants;
import com.example.admin.sdosandroidcars.api.APICall;
import com.example.admin.sdosandroidcars.api.APICallbackListener;
import com.example.admin.sdosandroidcars.api.info.Filter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cars {

    private final static String TAG = "Cars";
    private final static String GET_CARS_URL = Constants.getUrlFor("getCars");
    private final static String MAKER_URL = Constants.getUrlFor("addMaker");
    private final static String COLOR_URL = Constants.getUrlFor("addColor");
    private final static String CAR_URL = Constants.getUrlFor("addCar");

    private Cars() {}

    public static void doGetCars(Filter filter, final FilteredCarsResultListener crs) {
        Log.d(TAG, "doGetCars() cridat");

        APICall getCarsCall = new APICall("POST", GET_CARS_URL);

        getCarsCall.setRequestJson(filter.getJSONObject());


        getCarsCall.setOnAPICallbackListener(new APICallbackListener() {
            @Override
            public void onAPICallback(JSONObject json) {
                Log.d(TAG, "doGetCars:onAPICallback cridat");

                if (json == null)
                    crs.onCarsResult(null);
                else {
                    ArrayList<Car> cars = new ArrayList<>();

                    JSONArray rows = json.optJSONArray("rows");

                    for (int i = 0; i < rows.length(); i++) {
                        JSONObject car = rows.optJSONObject(i);

                        try {
                            int id = car.getInt("id");
                            String name = car.getString("name");
                            String color = car.getJSONObject("color").getString("name");
                            String maker = car.getJSONObject("maker").getString("name");
                            String url = car.getString("url");

                            cars.add(new Car(id, name, color, maker, url));
                        } catch (JSONException e) {
                            Log.e(TAG, "Error instanciant Car a partir de json");
                            e.printStackTrace();
                        }
                    }

                    crs.onCarsResult(cars);
                }
            }
        });
        getCarsCall.doAPICall();
    }

    public static void doAddMaker(JSONObject makerJson, final CarsResultListener crs) throws Exception {
        Log.d(TAG, "doAddMaker started");

        APICall addMakerCall = new APICall("POST", MAKER_URL);

        addMakerCall.setRequestJson(makerJson);

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

        addMakerCall.setRequestJson(colorJson);

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

        addMakerCall.setRequestJson(carJson);

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
