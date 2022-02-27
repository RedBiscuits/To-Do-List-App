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
import db.LoginDatabaseHandler;

public class Registration extends AppCompatActivity {
    Intent navigateToLogin;
    Button registerButton;
    TextView loginText;
    EditText username;
    EditText password;
    EditText confirmation;
    LoginDatabaseHandler dbHelper;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);
        confirmation = findViewById(R.id.password_confirmation_field);

        dbHelper = new LoginDatabaseHandler(this);

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
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String conf = confirmation.getText().toString();

                if(user.equals("")||pass.equals("")||conf.equals("")){
                    Toast.makeText(Registration.this , "Empty fields are not allowed",Toast.LENGTH_LONG).show();
                }else{
                    if(pass.equals(conf)){
                        Boolean checkUser = dbHelper.checkUsername(user);
                        if(!checkUser){
                            Boolean insert = dbHelper.insertData(user,pass);
                            if(insert){
                                Toast.makeText(Registration.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                                navigateToLogin();
                            }else{
                                Toast.makeText(Registration.this,"Registered Failed!!",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(Registration.this,"User Already Exists!!",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(Registration.this,"Passwords is not matching!",Toast.LENGTH_LONG).show();
                    }
                }

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