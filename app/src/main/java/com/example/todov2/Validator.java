package com.example.todov2;

import android.content.Context;

import java.util.ArrayList;

import db.Credentials;
import db.DatabaseAdapter;
import db.Task;

public class Validator {
    Context activity;
    public Validator(Context context) {
        this.activity = context;
    }

    DatabaseAdapter adapter = new DatabaseAdapter(activity);
    public ArrayList<Credentials> getUsers(){
        return adapter.getUsers();
    }

    public ArrayList<Task> getTask(){
        return adapter.getTasks();
    }
    public long addUser(String username,String password){
        long valid = 0;
        valid = adapter.addUser(new Credentials(username , password));
        return valid;
    }
    public long addTask(String task,int status){
        long valid = 0;
        valid  = adapter.addTask(new Task(task , status));
        return valid;
    }
}
