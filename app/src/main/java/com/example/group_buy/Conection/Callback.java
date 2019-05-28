package com.example.group_buy.Conection;

import org.json.JSONException;
import org.json.JSONObject;

public interface Callback {
   public void callback(JSONObject response, boolean error) throws JSONException;

}
