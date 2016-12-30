package com.example.admin.sdosandroidcars.api.cars;


import com.example.admin.sdosandroidcars.api.info.Element;

import org.json.JSONException;
import org.json.JSONObject;

public class Car2 {
    private final Element maker;
    private final Element color;
    private final String name;
    private final String img;

    public Car2(String name, Element color, Element maker, String img) {
        this.name = name;
        this.color = color;
        this.maker = maker;
        this.img = img;
    }

    public JSONObject getJSONObject() {
        JSONObject carObj = new JSONObject();

        try {
            carObj.put("name", name);
            carObj.put("maker", maker.getJSONObject());
            carObj.put("color", color.getJSONObject());
            carObj.put("img", img);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return carObj;
    }
}
