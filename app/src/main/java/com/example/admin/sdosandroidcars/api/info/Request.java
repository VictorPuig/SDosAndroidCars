package com.example.admin.sdosandroidcars.api.info;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 15/12/2016.
 */

public class Request {

    private int offset;
    private int limit;
    private Filter filter;

    public Request (Filter filter, int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
        this.filter = filter;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public JSONObject getJSONObject () {
        JSONObject json = filter.getJSONObject();
        try {
            json.put("offset",this.offset);
            json.put("limit",this.limit);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
