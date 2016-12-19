package com.example.admin.sdosandroidcars.api.info;


import android.util.Log;

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
        Log.d(toString(), "Set selected:" + s);
        this.selected = s;
    }

    public void select() {
        setSelected(true);
    }

    public void unselect() {
        setSelected(false);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Element) {
            Element e = (Element) obj;

            return id == e.getId() && name.equals(e.getName());

        } else return false;
    }

    //Crea un nou objecte Json amb la id, nom i boolean selected del element
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("id", getId());
            obj.put("name", getName());
            obj.put("seleccionat", isSelected());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return obj;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", selected=" + selected +
                '}';
    }
}
