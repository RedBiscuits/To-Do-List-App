package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseAdapter {

    static DatabaseHelper dbHelper;
    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public long addUser(Credentials credentials){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_USERNAME , credentials.getUsername());
        contentValues.put(DatabaseHelper.COL_PASSWORD , credentials.getPassword());
        long id = db.insert(DatabaseHelper.TABLE_CREDENTIALS , null , contentValues);
        return id;
    }

    public long addTask(Task task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_TASK , task.getDescription());
        contentValues.put(DatabaseHelper.COL_STATUS ,task.getStatus());
        long id = db.insert(DatabaseHelper.TABLE_TASKS , null , contentValues);
        return id;
    }

    public ArrayList<Credentials> getUsers(){
        ArrayList<Credentials> credentials = null;
        Cursor c;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COL_USERNAME , DatabaseHelper.COL_PASSWORD};
        c = db.query(DatabaseHelper.TABLE_CREDENTIALS , columns , null,null,null,null,null);
        while(c.moveToNext()){
            credentials.add(new Credentials(c.getString(0),c.getString(1)));
        }
        return credentials;
    }

    public ArrayList<Task> getTasks(){
        ArrayList<Task> task = null;
        Cursor c;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COL_TASK , DatabaseHelper.COL_STATUS};
        c = db.query(DatabaseHelper.TABLE_TASKS , columns , null,null,null,null,null);
        while(c.moveToNext()){
            task.add(new Task(c.getString(0),Integer.parseInt(c.getString(1))));
        }
        return task;
    }

    static class DatabaseHelper extends SQLiteOpenHelper{

        private static final int VERSION = 1;
        private static final String DATABASE_NAME = "todo_db.db";
        private static final String COL_UID = "_id";

        private static final String TABLE_CREDENTIALS = "CREDENTIALS";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";
        private static final String CREATE_CREDENTIALS_TABLE = "CREATE TABLE "+TABLE_CREDENTIALS+
                " ("+COL_UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_USERNAME+" TEXT, "
                + COL_PASSWORD+" TEXT );";


        private static final String TABLE_TASKS = "TASK";
        private static final String COL_TASK = "task";
        private static final String COL_STATUS = "status";

        private static final String CREATE_TASKS_TABLE = "CREATE TABLE "+TABLE_TASKS+
                " ("+COL_UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_TASK+" TEXT, "
                + COL_STATUS+" INTEGER );";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null,VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_CREDENTIALS_TABLE);
            sqLiteDatabase.execSQL(CREATE_TASKS_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CREDENTIALS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_TASKS);
            onCreate(sqLiteDatabase);

        }


    }
}
