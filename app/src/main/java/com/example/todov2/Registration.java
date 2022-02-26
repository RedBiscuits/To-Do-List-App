package com.example.todov2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import db.Credentials;

public class Registration extends AppCompatActivity {
    Intent navigateToLogin;
    Button registerButton;
    TextView loginText;
    EditText username;
    EditText password;
    EditText confirmation;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);
        confirmation = findViewById(R.id.password_confirmation_field);


        loginText = findViewById(R.id.login_inquiry);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLogin();
            }
        });

        registerButton = findViewById(R.id.login_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });}

    void navigateToLogin(){
        navigateToLogin = new Intent(Registration.this , MainActivity.class);
        startActivity(navigateToLogin);
    }

    short register(String username,String password,String confirmation){
        if(!password.equals(confirmation)){
            return 1;
        }else{
             return 0;
        }
    }
}