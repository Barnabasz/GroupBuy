package com.example.group_buy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.group_buy.Conection.Callback;
import com.example.group_buy.Conection.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Start extends AppCompatActivity {
    private TextView mTextMessage;
    private Button btLogin;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        checkSession();
        mTextMessage = findViewById(R.id.message);
        btLogin = (Button) findViewById(R.id.btLogin);
        btRegister = (Button) findViewById(R.id.btRegister);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void checkSession() {
        final Intent intent = new Intent(this, MainActivity.class);
        HttpRequest httpRequest = HttpRequest.getInstance(this);
        httpRequest.checkSession(getApplicationContext(), new Callback() {
            @Override
            public void callback(JSONObject response, boolean error) throws JSONException {
                if (!error) {
                    if (response.get("status").equals(true)) {
                        startActivity(intent);
                    }
                }
            }
        });
    }
}