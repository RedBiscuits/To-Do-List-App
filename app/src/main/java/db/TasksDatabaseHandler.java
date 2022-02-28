package db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TasksDatabaseHandler extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String NAME = "toDoListDatabase";
    public static final String TODO_TABLE = "todo";
    public static final String ID = "id";
    public static final String TASK = "task";
    public static final String STATUS = "status";
    public static final String CREATE_TODO_TABLE = "CREATE TABLE "+TODO_TABLE + "(" +ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +TASK + "TEXT, " + STATUS + " INTEGER)";

    private SQLiteDatabase db;
    public TasksDatabaseHandler(Context context){
        super(context , NAME , null , VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(new StringBuilder().append("DROP TABLE IF EXISTS").append(TODO_TABLE).toString());
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void insertTask(Task task){
        ContentValues contentValues= new ContentValues();
        contentValues.put(STATUS , task.getStatus());
        db.insert(TODO_TABLE,null,contentValues);
        contentValues.put(TASK , task.getTask());
    }

    @SuppressLint("Range")
    public List<Task> getAllTasks(){
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor=db.query(TODO_TABLE,null,null,null,null,null,null);
            if(cursor!=null){
                if(cursor.moveToFirst()){
                    do {
                        Task task = new Task() ;
                        task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        tasks.add(task);
                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return tasks;
    }

    public void updateStatus(int id , int status){
        ContentValues contentValues= new ContentValues();
        contentValues.put(STATUS , status);
        db.update(TODO_TABLE,contentValues,ID+"=?",new String[]{String.valueOf(id)});
    }

    public void updateTask(int id , String task){
        ContentValues contentValues= new ContentValues();
        contentValues.put(TASK , task);
        db.update(TODO_TABLE,contentValues,ID+"=?",new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE,ID+"=?",new String[]{String.valueOf(id)});
    }

}
