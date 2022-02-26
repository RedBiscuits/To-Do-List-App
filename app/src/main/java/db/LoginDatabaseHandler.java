package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LoginDatabaseHandler extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String NAME = "loginDatabase";
    public static final String USERS_TABLE = "users";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String CREATE_LOGIN_TABLE = "CREATE TABLE " + USERS_TABLE + "("
            + USERNAME + "TEXT PRIMARY KEY , " + PASSWORD + " TEXT)";

    public LoginDatabaseHandler(Context context) {
        super(context,NAME, null,VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(new StringBuilder().append("DROP TABLE IF EXISTS").append(USERS_TABLE).toString());
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, username);
        contentValues.put(PASSWORD, password);
        long result = db.insert(USERS_TABLE, null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " +
                USERNAME + " = ?", new String[]{username});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public Boolean checkUsernameAndPassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + USERNAME +
                " = ? and " + PASSWORD + " = ?", new String[]{username, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }
}