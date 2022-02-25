package com.example.todov2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import db.Credentials;

public class MainActivity extends AppCompatActivity {
    TextView register;
    Intent navigateToRegister;
    EditText username;
    EditText password;
    Validator validator = new Validator(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRegister = new Intent(MainActivity.this, Registration.class);
                ArrayList<Credentials> users = validator.getUsers();
                for (int i = 0 ; i < users.size(); i++){
                    if(users.get(i).getUsername().equals(username.toString())){
                        if(users.get(i).getPassword().equals(password.toString())){
                            startActivity(navigateToRegister);
                            break;
                        }else {
                            Toast.makeText(MainActivity.this,"Wrong password" , Toast.LENGTH_LONG);
                            break;
                        }
                    }
                }
                    Toast.makeText(MainActivity.this,"User not found" , Toast.LENGTH_LONG);
            }
        });
    }
}