package com.example.admin.sdosandroidcars.api.info;


import org.json.JSONException;
import org.json.JSONObject;

public class Element {
    private int id;
    private String name;
    private boolean selected;

    public Element(int id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public Element(int id, String name) {
        this(id, name, false);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean s) {
        this.selected = selected;
    }

    public void select() {
        setSelected(true);
    }

    public void unselect() {
        setSelected(false);
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("id", getId());
            obj.put("name", getName());
            obj.put("selected", isSelected());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return obj;
    }
}
