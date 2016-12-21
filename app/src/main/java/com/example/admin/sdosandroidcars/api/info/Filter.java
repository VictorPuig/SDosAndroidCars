package com.example.admin.sdosandroidcars.api.info;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Filter {

    private ArrayList<Element> colors;
    private ArrayList<Element> makers;

    Filter(ArrayList<Element> colors, ArrayList<Element> makers) {
        this.colors = colors;
        this.makers = makers;
    }

    public Filter() {
        this(new ArrayList<Element>(), new ArrayList<Element>());
    }

    public boolean isEmpty() {
        return colors.size() == 0 && makers.size() == 0;
    }

    public void setColors(ArrayList<Element> colors) {
        this.colors = colors;
    }

    public void setMakers(ArrayList<Element> makers) {
        this.makers = makers;
    }

    public void addColor(Element color) {
        colors.add(color);
    }

    public void addMaker(Element maker) {
        makers.add(maker);
    }

    public Element findColorById(int id) {
        for (Element e : colors)
            if (e.getId() == id)
                return e;

        return null;
    }

    public Element findMakerById(int id) {
        for (Element e : makers)
            if (e.getId() == id)
                return e;

        return null;
    }

    public ArrayList<Element> getSelectedColors() {
        ArrayList<Element> selected = new  ArrayList<>();

        for (Element e : colors)
            if (e.isSelected())
                selected.add(e);

        return selected;
    }

    public ArrayList<Element> getSelectedMakers() {
        ArrayList<Element> selected = new  ArrayList<>();

        for (Element e : makers)
            if (e.isSelected())
                selected.add(e);

        return selected;
    }

    public Filter getSelected() {
        Filter filter = new Filter();

        filter.setColors(getSelectedColors());

        filter.setMakers(getSelectedMakers());

        return filter;
    }

    public ArrayList<Element> getColors() {
        return colors;
    }

    public ArrayList<Element> getMakers() {
        return makers;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Filter) {
            Filter f = (Filter) obj;

            return getColors().equals(f.getColors()) && getMakers().equals(f.getMakers());

        } else return false;
    }

    //Crea un nou objecte Json amb dos arrays d'objectes Json (1 array de makers i 1 array de colors)
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();

        JSONArray colorArray = new JSONArray();
        for (Element e : colors)
            colorArray.put(e.getJSONObject());

        JSONArray makerArray = new JSONArray();
        for (Element e : makers)
            makerArray.put(e.getJSONObject());

        try {
            obj.put("color", colorArray);
            obj.put("maker", makerArray);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return obj;
    }

    public ArrayList<String> getStringColorJson (JSONObject json) {

        JSONArray colorArray = new JSONArray();
        for (Element e : colors)
            colorArray.put(e.getJSONObject());

        ArrayList<String> colorString = new ArrayList<>();

        for (int i = 0; i < colorArray.length(); i++) {
            try {
                colorString.add(colorArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return colorString;
    }

    public ArrayList<String> getStringMakerJson (JSONObject json) {

        JSONArray makerArray = new JSONArray();
        for (Element e : makers)
            makerArray.put(e.getJSONObject());

        ArrayList<String> makerString = new ArrayList<>();

        for (int i = 0; i < makerArray.length(); i++) {
            try {
                makerString.add(makerArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return makerString;
    }
}
