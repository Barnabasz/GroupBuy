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
import java.util.Objects;

public class Register extends AppCompatActivity {

    private static final String TAG = Register.class.getName();
    HttpRequest httpRequest = HttpRequest.getInstance(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final Intent intent = new Intent(this, MainActivity.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map data = getData();
                if(registerValidate(data)) {
                    httpRequest.sendRequest(data, "","public/users/register", Request.Method.POST, new Callback() {
                        @Override
                        public void callback(JSONObject response, boolean error) throws JSONException {
                            if (!error) {
                                response.toString();
                                if (response.get("status").equals(true)) {
                                    Session session = new Session(getApplicationContext());
                                    session.saveSession(data, response.getString("id"));
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                }
            }
        });
    }



    private  Map getData(){
        final EditText etLogin = (EditText) findViewById(R.id.etLogin);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etRePassword = (EditText) findViewById(R.id.etRePassword);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "username", etLogin.getText().toString());
        data.put( "email", etEmail.getText().toString());
        data.put( "password", etPassword.getText().toString());
        data.put( "rePassword", etRePassword.getText().toString());
        return data;
    }
    private boolean registerValidate(Map data){
        if (Objects.requireNonNull(data.get("email")).toString().equals("")) {
            Log.i(TAG, "Invalid register data");
            return false;
        } else if (Objects.requireNonNull(data.get("username")).toString().equals("")) {
            Log.i(TAG, "Invalid register data");
            return false;
        } else if (Objects.requireNonNull(data.get("password")).toString().equals("")) {
            Log.i(TAG, "Invalid register data");
            return false;
        }else if (!Objects.requireNonNull(data.get("password")).toString().equals(Objects.requireNonNull(data.get("rePassword")).toString())) {
            Log.i(TAG, "Passwords dont match");
            return false;
        }else {
            return true;
        }
    }
}
