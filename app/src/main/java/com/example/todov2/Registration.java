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
    Validator validator = new Validator(Registration.this);

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

                short result = register(username.toString(),password.toString(),confirmation.toString());
                if(result == 1){
                    Toast.makeText(Registration.this,"Passwords don't match!" , Toast.LENGTH_LONG);
                }else if(result == 2){
                    Toast.makeText(Registration.this,"Username is not available" , Toast.LENGTH_LONG);
                }else{
                    Toast.makeText(Registration.this,"Registered successfully!" , Toast.LENGTH_LONG);
                    navigateToLogin();
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
            ArrayList<Credentials> users = validator.getUsers();
            for(int i = 0 ; i < users.size();i++){
                if(username.equals(users.get(i).getUsername())){
                    return 2;
                }
            }
            validator.addUser(username,password);
            return 0;
        }
    }
}