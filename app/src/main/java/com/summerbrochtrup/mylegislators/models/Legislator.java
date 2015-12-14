package com.summerbrochtrup.mylegislators.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Legislator {

    private String mName;

    public Legislator(JSONObject legislatorObject) throws JSONException {
        try {
            mName = legislatorObject.getString("first_name") + " " + legislatorObject.getString("last_name");
        } catch (JSONException e) {
           e.printStackTrace();
        }
    }

    public String getName() {
        return mName;
    }
}
