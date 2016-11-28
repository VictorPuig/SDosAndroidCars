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

    public Info() {

    }

    public static void doGetInfo(final InfoResultListener infoResultListener) {
        Log.d(TAG, "doGetInfo started");

        APICall signupCall = new APICall(INFO_URL);

        //Listener per fer algo quan acaba l'async task d'ApiCall
        //setOnAPICallbackListener crida a Info quan ha acabat
        signupCall.setOnAPICallbackListener(new APICallbackListener() {
            @Override
            //Metode que s'executara un cop hagui finalitzat l'async task d'ApiCall
            public void onAPICallback(JSONObject json) {
                Log.d(TAG, "onAPICallback cridat");

                //Creació del filter
                Filter filter = new Filter();

                //Si no ha retornat json vol dir que ja tenim el filtre???
                if (json == null) {
                    infoResultListener.onInfoResult(filter);
                    return;
                }

                //colors conte la array de colors del json retornat del servidor
                JSONArray colors = json.optJSONArray("color");
                //Recorrem l'array de colors i afegim els elements amb id i nom del color trobat al filter
                for (int i = 0; i < colors.length(); i++) {
                    JSONObject colorObj = colors.optJSONObject(i);

                    if (colorObj == null) continue;

                    int colorId = colorObj.optInt("id");
                    String colorName = colorObj.optString("name");
                    Element colorEl = new Element(colorId, colorName);
                    filter.addColor(colorEl);
                }

                //makers conte la array de makers del json retornat del servidor
                JSONArray makers = json.optJSONArray("maker");
                //Recorrem l'array de makers i afegim els elements amb id i nom del maker trobat al filter
                for (int i = 0; i < makers.length(); i++) {
                    JSONObject makerObj = makers.optJSONObject(i);

                    if (makerObj == null) continue;

                    int colorId = makerObj.optInt("id");
                    String makerName = makerObj.optString("name");
                    Element makerEl = new Element(colorId, makerName);
                    filter.addMaker(makerEl);
                }

                //Avisem a Drawer (main) que ja ha acabat l'execucio i li pasem el filter amb les objectes demanats al servidor
                infoResultListener.onInfoResult(filter);
            }
        });
        //Execució del metode doAPICall que executa el post /getInfo demanant les dades del filter
        signupCall.doAPICall();
    }
}
