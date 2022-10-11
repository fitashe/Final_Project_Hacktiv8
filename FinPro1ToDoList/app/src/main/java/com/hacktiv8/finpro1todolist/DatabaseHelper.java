package com.hacktiv8.finpro1todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "todolist";
    public static final int DB_VERSION = 1;

    public static final String TABLE_TASK = "tasks";

    public static final String KEY_ID = "id";
    public static final String TITLE_TASK = "title";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tasksTable = " CREATE TABLE " + TABLE_TASK + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE_TASK + " TEXT NOT NULL ) ";

        sqLiteDatabase.execSQL(tasksTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(sqLiteDatabase);
    }

    public List<ToDoList> getAllData(){

        List<ToDoList> toDoLists = new ArrayList<>();

        String allCountry = "SELECT * FROM "+TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(allCountry, null);

        if(cursor.moveToFirst()){
            do{
                ToDoList list = new ToDoList();
                list.setId(Integer.parseInt(cursor.getString(0)));
                list.setTitle(cursor.getString(1));

                toDoLists.add(list);

            }while (cursor.moveToNext());
        }

        return toDoLists;

    }

    public void addTask(String title){
        String insertData = "INSERT INTO "+ TABLE_TASK + " ("+ TITLE_TASK +") VALUES ('"+title +"')";
        this.getWritableDatabase().execSQL(insertData);
    }

    public Cursor query(boolean b, String table, String[] columns, String selection,
                        String[] selectionArgs, String by, String groupBy, String having,
                        String orderBy) {

        return query(false, table, columns, selection, selectionArgs, groupBy,
                having, orderBy, null /* limit */);
    }

    public void deleteTask(int id){
        String deleteData = "DELETE FROM "+TABLE_TASK +" WHERE id="+id;
        this.getWritableDatabase().execSQL(deleteData);
    }

}
