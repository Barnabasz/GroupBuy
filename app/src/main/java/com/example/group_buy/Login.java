package com.example.group_buy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.example.group_buy.Conection.Callback;
import com.example.group_buy.Conection.HttpRequest;
import com.example.group_buy.Conection.Session;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private static final String TAG = Login.class.getName();
    HttpRequest httpRequest = HttpRequest.getInstance(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final Intent intent = new Intent(this, MainActivity.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map data = getData();
                httpRequest.sendRequest(data, "","public/users/login", Request.Method.POST, new Callback() {
                    @Override
                    public void callback(JSONObject response, boolean error) throws JSONException {
                        if (!error)
                            if (response.get("status").equals(true)) {
                                Session session = new Session(getApplicationContext());
                                session.saveSession(data, response.getString("id"));
                                Log.i(TAG, response.toString());
                                startActivity(intent);
                            }
                    }
                });
            }
        });
    }

    private Map getData() {
        EditText etLogin = (EditText) findViewById(R.id.etLogin);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "username", etLogin.getText().toString());
        data.put( "password", etPassword.getText().toString());
        return data;
    }



}
