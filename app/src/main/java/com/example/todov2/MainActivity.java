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

public class MainActivity extends AppCompatActivity {
    TextView register;
    Intent navigateToRegister;
    Intent navigateToTasks;
    EditText username;
    EditText password;
    Button loginButton;
    LoginDatabaseHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);
        DB = new LoginDatabaseHandler(this);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals("")){
                    Toast.makeText(MainActivity.this,"Please fill both fields!",Toast.LENGTH_LONG).show();
                }else{
                    Boolean checkUser = DB.checkUsernameAndPassword(user,pass);
                    if(checkUser){
                        navigateToTasks();
                    }else{
                        Toast.makeText(MainActivity.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    navigateToRegister();
                }
        });
    }
    public void navigateToTasks(){
        navigateToTasks = new Intent(MainActivity.this , TasksActivity.class);
        startActivity(navigateToTasks);
    }
    public void navigateToRegister(){
        navigateToRegister = new Intent(MainActivity.this, Registration.class);
        startActivity(navigateToRegister);
    }
}