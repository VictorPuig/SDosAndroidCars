package com.example.admin.sdosandroidcars.api.info;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Filter {

    ArrayList<Element> colors;
    ArrayList<Element> makers;

    Filter(ArrayList<Element> colors, ArrayList<Element> makers) {
        this.colors = colors;
        this.makers = makers;
    }

    Filter() {
        this(new ArrayList<Element>(), new ArrayList<Element>());
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
        ArrayList<Element> selected = new  ArrayList<Element>();

        for (Element e : colors)
            if (e.isSelected())
                selected.add(e);

        return selected;
    }

    public ArrayList<Element> getSelectedMakers() {
        ArrayList<Element> selected = new  ArrayList<Element>();

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

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();

        JSONArray colorArray = new JSONArray();
        for (Element e : colors)
            colorArray.put(e.getJSONObject());

        JSONArray makerArray = new JSONArray();
        for (Element e : makers)
            makerArray.put(e.getJSONObject());

        try {
            obj.put("colors", colorArray);
            obj.put("makers", makerArray);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return obj;
    }
}
