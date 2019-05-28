package com.example.group_buy.Conection;

import android.content.Context;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static HttpRequest instance;
    public static HttpRequest getInstance(AppCompatActivity activity){
        instance = new HttpRequest(activity);
        return instance;
    }
    private AppCompatActivity context;
    private String TAG;
    private HttpRequest(AppCompatActivity activity){
        context = activity;
        TAG = context.getClass().getName();
    }
    public void GetRequest(final Map data, final String id, final String urlSuffix, final Callback coll){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://ec2-3-120-128-193.eu-central-1.compute.amazonaws.com:8080/";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url + urlSuffix,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", "Alif");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", id);
                headers.put("content-type", "application/json;charset=UTF-8");
                return headers;
            }
        };
        queue.add(getRequest);
    }
    public void sendRequest(final Map data, final String id, final String queryString, int method, final Callback coll){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://ec2-3-120-128-193.eu-central-1.compute.amazonaws.com:8080/";
        JsonObjectRequest JSONObj = new JsonObjectRequest(method, url + queryString, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i(TAG, queryString + " Response: " + response.toString());
                            coll.callback(response, false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            Log.i(TAG, queryString + " Error: " + error.toString());
                            coll.callback(null, true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", id);
                headers.put("content-type", "application/json;charset=UTF-8");
                return headers;
            }
        };

        Log.i(TAG, queryString + " Body: " + new String(JSONObj.getBody()));
        JSONObj.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(JSONObj);
    }
    public void checkSession(Context cont, final Callback coll){
        Session session = new Session(cont);
        Map data = session.getData();
        sendRequest(data, session.getID(),"users/checkSession", Request.Method.GET,  coll);
    }
    public void logOut(Context cont) {
        Session session = new Session(cont);
        Map data = session.getData();
        sendRequest(data, session.getID(), "users/logout", Request.Method.POST, new Callback() {
            @Override
            public void callback(JSONObject response, boolean error) throws JSONException {}
        });
    }
}