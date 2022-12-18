package com.example.mini_projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.SyncStateContract;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static class Constant implements BaseColumns {
        private static final String DATABASE_NAME = "TaskManager.db";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_USER = "user";
        private static final String USER_ID = "user_id";
        private static final String USER_NAME = "user_name";
        private static final String USER_PASSWORD = "user_password";
        private static final String USER_EMAIL = "user_email";
        private static final String USER_CONNECTIVITY = "user_connectivity";

        private static final String TABLE_TASK = "task";
        private static final String TASK_ID = "task_id";
        private static final String TASK_LABEL = "task_label";
        private static final String TASK_DISCRIP = "task_disrip";
        private static final String TASK_TIME = "task_time";
        private static final String TASK_PRIORITY = "task_priority";
        private static final ArrayList<String> priorities = new ArrayList<String>(){
            {
              add("urgent , important");
              add("urgent , pas important");
              add("pas urgent , important");
              add("pas urgent , pas important");
            }
        };
    }

    public DataBaseHelper(@Nullable Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_user= "CREATE TABLE " + Constant.TABLE_USER +
                " (" + Constant.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constant.USER_NAME + " TEXT, " +
                Constant.USER_PASSWORD + " TEXT, "+
                Constant.USER_EMAIL + " TEXT, " +
                Constant.USER_CONNECTIVITY + " INTEGER);"; // 1 for only connected compte and 0 for the rest (update)

        String query_task= "CREATE TABLE " + Constant.TABLE_TASK +
                " (" + Constant.TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constant.TASK_LABEL + " TEXT, " +
                Constant.TASK_DISCRIP + " TEXT, " +
                Constant.TASK_PRIORITY + " INTEGER, " +
                Constant.TASK_TIME + " INTEGER ,"+
                Constant.USER_ID + " INTEGER);";
        db.execSQL(query_user);
        db.execSQL(query_task);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_TASK);
        onCreate(db);
    }

    public void saveUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(Constant.USER_NAME,user.getNom_user());
        values.put(Constant.USER_PASSWORD,user.getMp_user());
        values.put(Constant.USER_EMAIL,user.getEmail_user());
        values.put(Constant.USER_CONNECTIVITY,1);
        long result = db.insert(Constant.TABLE_USER,null, values);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addTask(User user,Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(Constant.TASK_LABEL,task.getLabel_task());
        values.put(Constant.TASK_DISCRIP,task.getDescription_task());
        values.put(Constant.TASK_PRIORITY,Constant.priorities.indexOf(task.getPriority_task()));
        values.put(Constant.TASK_TIME,Integer.valueOf(task.getTime_task()));
        values.put(Constant.USER_ID,find_Id_User(user));
        long result = db.insert(Constant.TABLE_TASK,null, values);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTask(String task_id,Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(Constant.TASK_LABEL,task.getLabel_task());
        values.put(Constant.TASK_DISCRIP,task.getDescription_task());
        values.put(Constant.TASK_TIME,task.getTime_task());
        values.put(Constant.TASK_PRIORITY,Constant.priorities.indexOf(task.getPriority_task()));

        long result = db.update(Constant.TABLE_TASK, values, "task_id=?", new String[]{task_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteTask(String task_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Constant.TABLE_TASK, "task_id=?", new String[]{task_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    User findUser(String name_user){
        String req = "SELECT * FROM "+Constant.TABLE_USER +" WHERE "+Constant.USER_NAME + " = '" + name_user + "' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if(db != null){
            cursor = db.rawQuery(req, null);
            if(cursor.moveToFirst()) {
                do{
                    User user = new User(cursor.getString(1),cursor.getString(2),cursor.getString(3));
                    return user;
                }while (cursor.moveToNext());

            }
        }
        return null;
    }

    void connectUser(String name_user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + Constant.TABLE_USER + " SET " + Constant.USER_CONNECTIVITY + " = 1 WHERE "+Constant.USER_NAME+" = '"+name_user+"' ;");
    }
    User connectedUser(){
        String req = "SELECT * FROM "+Constant.TABLE_USER +" WHERE "+Constant.USER_CONNECTIVITY + " = 1 ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(req, null);
            if(cursor.moveToFirst()) {
                do{
                    User user = new User(cursor.getString(1),cursor.getString(2),cursor.getString(3));
                    return user;
                }while (cursor.moveToNext());

            }
        }
        return null;
    }



    ArrayList<Task> findTASKS_USER(User user){
        ArrayList<Task> tasks = new ArrayList<Task>();
        String req = "SELECT * FROM "+Constant.TABLE_TASK+" WHERE "+Constant.USER_ID+" = "+find_Id_User(user);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if(db != null){
            cursor = db.rawQuery(req, null);
            if(cursor.moveToFirst()) {
                do{
                    Task task = new Task(cursor.getString(1),cursor.getString(2),cursor.getString(4), Constant.priorities.get(Integer.parseInt(cursor.getString(3))));
                    tasks.add(task);
                }while (cursor.moveToNext());
                return tasks;
            }
        }
        return null;
    }

    Integer find_Id_User(User user){
        String req = "SELECT * FROM "+Constant.TABLE_USER +" WHERE "+Constant.USER_NAME + " = '" + user.getNom_user() + "' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(req, null);
            if(cursor.moveToFirst()) {
                return Integer.valueOf(cursor.getString(0));
            }
        }
        return null;
    }

    String find_Task_Id_From_List(User user,String row){
        String req = "SELECT * FROM "+Constant.TABLE_TASK+" WHERE "+Constant.USER_ID+" = "+find_Id_User(user)+" LIMIT 1 OFFSET "+row+" ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(req, null);
            if(cursor.moveToFirst()) {
                return cursor.getString(0);
            }
        }
        return null;
    }

    void disconnected(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + Constant.TABLE_USER + " SET " + Constant.USER_CONNECTIVITY + " = 0 ;");
    }


}
